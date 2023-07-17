package com.mtfm.app_purchase.service.purchase.impl;

import com.mtfm.app_purchase.service.purchase.CommodityService;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.CommodityManager;
import com.mtfm.purchase.manager.SpuManager;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommodityView;
import com.mtfm.purchase.manager.provisioning.Spu;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;

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
    public Spu.SpuDetails loadSpuDetails(long spuId) {
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
