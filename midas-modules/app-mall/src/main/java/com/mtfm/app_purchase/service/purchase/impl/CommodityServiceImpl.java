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

import com.mtfm.app_purchase.service.purchase.CommodityService;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.CommodityManager;
import com.mtfm.purchase.manager.SpuManager;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommodityView;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品业务
 */
public class CommodityServiceImpl implements CommodityService {

    private CommodityManager commodityManager;

    private SpuManager spuManager;

    public CommodityServiceImpl(CommodityManager commodityManager, SpuManager spuManager) {
        this.commodityManager = commodityManager;
        this.spuManager = spuManager;
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
    public SpuDetails loadSpuDetails(long spuId) {
        return this.spuManager.loadSpuDetailsById(spuId);
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
}
