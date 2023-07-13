package com.mtfm.purchase.manager.service;

import com.mtfm.purchase.manager.AttributeManager;
import com.mtfm.purchase.manager.provisioning.AttributeGroupValue;

import java.util.List;

public class AttributeManageService implements AttributeManager {

    @Override
    public void setSpuAttributes(long spu, List<AttributeGroupValue> attributeGroupValues) {

    }

    @Override
    public void removeSpuAttributes(long spu, long[] attributes) {

    }

    @Override
    public List<AttributeGroupValue> loadAttributesBySpuId(long id) {
        return null;
    }

    @Override
    public void setCommodityAttributes(long commodity, List<AttributeGroupValue> attributeGroupValues) {

    }

    @Override
    public void removeCommodityAttributes(long commodity, long[] attributes) {

    }

    @Override
    public List<AttributeGroupValue> loadAttributesByCommodityId(long id) {
        return null;
    }
}
