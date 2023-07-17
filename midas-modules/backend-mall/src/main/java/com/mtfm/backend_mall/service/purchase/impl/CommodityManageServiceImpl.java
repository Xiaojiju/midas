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
import com.mtfm.backend_mall.service.purchase.MallCommodityManageService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.CommodityManager;
import com.mtfm.purchase.manager.SpuManager;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommoditySplitDetails;
import com.mtfm.purchase.manager.provisioning.Spu;
import com.mtfm.purchase.manager.service.bo.SplitPageQuery;
import com.mtfm.tools.enums.Judge;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品管理
 */
public class CommodityManageServiceImpl implements MallCommodityManageService {

    private SpuManager spuManager;

    private CommodityManager commodityManager;

    public CommodityManageServiceImpl(SpuManager spuManager, CommodityManager commodityManager) {
        this.spuManager = spuManager;
        this.commodityManager = commodityManager;
    }

    @Override
    public long createSpuDetails(Spu.SpuDetails spuDetails) {
        return this.spuManager.createSpu(spuDetails);
    }

    @Override
    public void updateSpuDetails(Spu.SpuDetails spuDetails) {
        this.spuManager.updateSpu(spuDetails);
    }

    @Override
    public void removeSpu(long spuId) {
        this.spuManager.removeSpuById(spuId);
    }

    @Override
    public Spu.SpuDetails getSpuDetailsById(long spuId) {
        try {
            return this.spuManager.loadSpuDetailsById(spuId);
        } catch (PurchaseNotFoundException e) {
            throw new ServiceException(e.getMessage(), MallCode.SPU_NOT_FOUND.getCode());
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
            throw new ServiceException(e.getMessage(), MallCode.SPU_NOT_FOUND.getCode());
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
        } catch (PurchaseNotFoundException e) {
            throw new ServiceException(e.getMessage(), MallCode.SPU_NOT_FOUND.getCode());
        }
    }

    @Override
    public void updateCommodityDetails(CommodityDetails commodityDetails) {
        try {
            this.commodityManager.updateCommodity(commodityDetails);
        } catch (PurchaseNotFoundException e) {
            throw new ServiceException(e.getMessage(), MallCode.SPU_NOT_FOUND.getCode());
        }
    }

    @Override
    public void removeCommodity(long commodityId) {
        this.commodityManager.deleteByCommodityId(commodityId);
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
}
