package com.mtfm.backend_mall.service.purchase.impl;

import com.mtfm.backend_mall.MallCode;
import com.mtfm.backend_mall.service.purchase.BrandService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.manager.BrandManager;
import com.mtfm.purchase.manager.provisioning.BrandDetails;

public class BrandServiceImpl implements BrandService {

    private final BrandManager brandManager;

    public BrandServiceImpl(BrandManager brandManager) {
        this.brandManager = brandManager;
    }

    @Override
    public void saveOne(BrandDetails details) {
        try {
            this.brandManager.createBrand(details);
        } catch (PurchaseExistException e) {
            throw new ServiceException(e.getMessage(), MallCode.BRAND_NAME_EXIST.getCode());
        }
    }
}
