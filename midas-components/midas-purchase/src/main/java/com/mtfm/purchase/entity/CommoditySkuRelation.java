package com.mtfm.purchase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 规格与商品的关联，只能一种规格对应多商品，不能一个商品对应多个同种规格
 */
@TableName("purchase_commodity_sku_relation")
public class CommoditySkuRelation implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 商品id
     */
    @TableField("commodity_id")
    private Long commodityId;
    /**
     * 规格id
     */
    @TableField("sku_id")
    private Long skuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
