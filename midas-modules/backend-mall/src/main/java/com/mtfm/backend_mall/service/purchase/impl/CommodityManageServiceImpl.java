/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.backend_mall.service.purchase.impl;

import com.mtfm.backend_mall.MallCode;
import com.mtfm.backend_mall.service.provisioning.CommoditySetting;
import com.mtfm.backend_mall.service.purchase.MallCommodityManageService;
import com.mtfm.core.ServiceCode;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.express.manager.ExpressRelationManager;
import com.mtfm.express.manager.provisioning.ExpressSetting;
import com.mtfm.purchase.PurchaseMessageSource;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.exceptions.PurchaseRelationException;
import com.mtfm.purchase.manager.CommodityManager;
import com.mtfm.purchase.manager.SpuManager;
import com.mtfm.purchase.manager.TagManager;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommoditySplitDetails;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.purchase.manager.service.bo.SplitPageQuery;
import com.mtfm.tools.enums.Judge;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品管理
 */
@Transactional(rollbackFor = Exception.class)
public class CommodityManageServiceImpl implements MallCommodityManageService, MessageSourceAware {

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();

    private SpuManager spuManager;

    private CommodityManager commodityManager;

    private TagManager tagManager;

    private ExpressRelationManager expressRelationManager;

    public CommodityManageServiceImpl(SpuManager spuManager, CommodityManager commodityManager,
                                      TagManager tagManager, ExpressRelationManager expressRelationManager) {
        this.spuManager = spuManager;
        this.commodityManager = commodityManager;
        this.tagManager = tagManager;
        this.expressRelationManager = expressRelationManager;
    }

    @Override
    public long createSpuDetails(SpuDetails spuDetails) {
        return this.spuManager.createSpu(spuDetails);
    }

    @Override
    public void updateSpuDetails(SpuDetails spuDetails) {
        this.spuManager.updateSpu(spuDetails);
    }

    @Override
    public void removeSpu(long spuId) {
        try {
            this.spuManager.removeSpuById(spuId);
        } catch (PurchaseNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), ServiceCode.DATA_NOT_FOUND.getCode());
        }
    }

    @Override
    public SpuDetails getSpuDetailsById(long spuId) {
        try {
            return this.spuManager.loadSpuDetailsById(spuId);
        } catch (PurchaseNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), ServiceCode.DATA_NOT_FOUND.getCode());
        }
    }

    @Override
    public void listing(long spuId, boolean listing) {
        try {
            if (listing) {
                this.spuManager.listing(spuId, Judge.YES);
            } else {
                this.spuManager.listing(spuId, Judge.NO);
            }
        } catch (PurchaseNotFoundException e) {
            throw new ServiceException(e.getMessage(), ServiceCode.DATA_NOT_FOUND.getCode());
        }
    }

    @Override
    public PageTemplate<CommoditySplitDetails> commodityPageList(SplitPageQuery query) {
        return this.commodityManager.loadPage(query);
    }

    @Override
    public List<CommodityDetails> getCommodityDetailsBySpuId(long spuId) {
        return this.commodityManager.loadCommodities(spuId);
    }

    @Override
    public long createCommodityDetails(CommodityDetails commodityDetails) {
        try {
            return this.commodityManager.createCommodity(commodityDetails);
        } catch (PurchaseNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), MallCode.SPU_NOT_FOUND.getCode());
        } catch (PurchaseRelationException relation) {
            throw new ServiceException(relation.getMessage(), MallCode.COMMODITY_SKU_NOT_MATCH.getCode());
        }
    }

    @Override
    public void updateCommodityDetails(CommodityDetails commodityDetails) {
        try {
            this.commodityManager.updateCommodity(commodityDetails);
        } catch (PurchaseNotFoundException e) {
            throw new ServiceException(e.getMessage(), MallCode.SPU_NOT_FOUND.getCode());
        } catch (PurchaseRelationException relation) {
            throw new ServiceException(relation.getMessage(), MallCode.COMMODITY_SKU_NOT_MATCH.getCode());
        }
    }

    @Override
    public void removeCommodity(long commodityId) {
        try {
            this.commodityManager.deleteByCommodityId(commodityId);
        } catch (PurchaseNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), ServiceCode.DATA_NOT_FOUND.getCode());
        }
    }

    @Override
    public void setSetting(CommoditySetting setting) {
        SpuDetails spuDetails = this.spuManager.loadSpuDetailsById(setting.getSpuId());
        if (spuDetails == null) {
            throw new ServiceException(this.messages.getMessage("SpuDetailsService.notFound",
                    "Unable to find the specified product"), MallCode.SPU_NOT_FOUND.getCode());
        }
        // 设置标签
        tagManager.setTags(setting.getSpuId(), setting.getTags());

        // 上架状态
        this.spuManager.listing(spuDetails.getId(), setting.getListing());

        // 关联物流信息
        this.expressRelationManager.setRelation(setting.getSpuId(), setting.getExpressSetting());
    }

    @Override
    public CommoditySetting loadSetting(long spuId) {
        SpuDetails spuDetails = this.spuManager.loadSpuDetailsById(spuId);
        if (spuDetails == null) {
            throw new ServiceException(this.messages.getMessage("SpuDetailsService.notFound",
                    "Unable to find the specified product"), MallCode.SPU_NOT_FOUND.getCode());
        }
        List<String> tags = this.tagManager.loadTags(spuId);
        ExpressSetting express = this.expressRelationManager.loadSetting(spuId);
        CommoditySetting setting = new CommoditySetting();
        setting.setExpressSetting(express);
        setting.setListing(spuDetails.getListing());
        setting.setTags(tags);
        setting.setSpuId(spuId);
        return setting;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected SpuManager getSpuManager() {
        return spuManager;
    }

    public void setSpuManager(SpuManager spuManager) {
        this.spuManager = spuManager;
    }

    protected CommodityManager getCommodityManager() {
        return commodityManager;
    }

    public void setCommodityManager(CommodityManager commodityManager) {
        this.commodityManager = commodityManager;
    }

    protected TagManager getTagManager() {
        return tagManager;
    }

    public void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
    }

    protected ExpressRelationManager getExpressRelationManager() {
        return expressRelationManager;
    }

    public void setExpressRelationManager(ExpressRelationManager expressRelationManager) {
        this.expressRelationManager = expressRelationManager;
    }
}
