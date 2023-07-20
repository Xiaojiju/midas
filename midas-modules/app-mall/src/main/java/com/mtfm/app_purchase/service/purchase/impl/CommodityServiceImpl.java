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
package com.mtfm.app_purchase.service.purchase.impl;

import com.mtfm.app_purchase.MallCode;
import com.mtfm.app_purchase.service.provisioning.SpuView;
import com.mtfm.app_purchase.service.purchase.CommodityService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.express.manager.ExpressRelationManager;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.CommodityManager;
import com.mtfm.purchase.manager.SpuManager;
import com.mtfm.purchase.manager.TagManager;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommodityView;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品业务
 */
public class CommodityServiceImpl implements CommodityService {

    private CommodityManager commodityManager;

    private SpuManager spuManager;

    private TagManager tagManager;

    private ExpressRelationManager expressRelationManager;

    public CommodityServiceImpl(CommodityManager commodityManager, SpuManager spuManager,
                                TagManager tagManager, ExpressRelationManager expressRelationManager) {
        this.commodityManager = commodityManager;
        this.spuManager = spuManager;
        this.tagManager = tagManager;
        this.expressRelationManager = expressRelationManager;
    }

    @Override
    public PageTemplate<CommodityView> loadViewPage(CommodityPageQuery query) {
        return this.commodityManager.loadCommodityViewPage(query);
    }

    @Override
    public CommodityDetails loadDetails(long commodityId) {
        return this.commodityManager.loadCommodityById(commodityId);
    }

    @Override
    public SpuView loadSpuDetails(long spuId) {
        try {
            SpuView spuView = new SpuView();
            SpuDetails spuDetails = this.spuManager.loadSpuDetailsById(spuId);
            List<String> tags = this.tagManager.loadTags(spuId);
            spuView.setSpuDetails(spuDetails);
            spuView.setTags(tags);
            spuView.setExpressSetting(this.expressRelationManager.loadSetting(spuId));
            return spuView;
        } catch (PurchaseNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), MallCode.SPU_NOT_FOUND.getCode());
        }
    }

    protected CommodityManager getCommodityManager() {
        return commodityManager;
    }

    public void setCommodityManager(CommodityManager commodityManager) {
        this.commodityManager = commodityManager;
    }

    protected SpuManager getSpuManager() {
        return spuManager;
    }

    public void setSpuManager(SpuManager spuManager) {
        this.spuManager = spuManager;
    }

    protected TagManager getTagManager() {
        return tagManager;
    }

    public void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
    }
}
