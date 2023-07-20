package com.mtfm.express.manager;

import com.mtfm.express.manager.provisioning.ExpressSetting;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品物流信息
 */
public interface ExpressRelationManager {
    /**
     * 商品关联物流信息
     * @param spuId 商品id
     * @param expressSetting 物流信息
     */
    void setRelation(long spuId, ExpressSetting expressSetting);

    /**
     * 商品的物流信息
     * @param spuId 商品id
     * @return 商品设定
     */
    ExpressSetting loadSetting(long spuId);
}
