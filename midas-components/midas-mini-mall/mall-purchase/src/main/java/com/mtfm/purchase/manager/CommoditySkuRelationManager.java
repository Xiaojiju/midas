package com.mtfm.purchase.manager;

import com.mtfm.purchase.manager.provisioning.SpuDetails;

import java.util.Collection;
import java.util.List;

public interface CommoditySkuRelationManager {

    /**
     * 获取具体商品关联的规格组
     * @param commodity 商品id
     * @return 商品关联的规格
     */
    List<SpuDetails.SkuVal> loadCommoditySkuItems(long commodity);

    /**
     * 设置具体商品规格
     * @param spuId 商品id
     * @param commodity 具体规格商品id
     * @param items 关联规格id
     */
    void withSku(long spuId, long commodity, List<Long> items);

}
