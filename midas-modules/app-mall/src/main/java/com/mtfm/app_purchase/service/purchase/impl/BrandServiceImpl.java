package com.mtfm.app_purchase.service.purchase.impl;

import com.mtfm.app_purchase.service.purchase.BrandService;
import com.mtfm.purchase.manager.BrandManager;
import com.mtfm.purchase.manager.provisioning.BrandDetails;

public class BrandServiceImpl implements BrandService {

    private final BrandManager brandManager;

    public BrandServiceImpl(BrandManager brandManager) {
        this.brandManager = brandManager;
    }

    @Override
    public BrandDetails getBrand(Long id) {
        return this.brandManager.loadBrand(id, null);
    }
}
