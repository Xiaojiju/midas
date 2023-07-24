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
package com.mtfm.cart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mtfm.cart.manager.provisioning.CartItemDetails;
import com.mtfm.datasource.BaseModel;
import org.springframework.util.Assert;

import java.io.Serializable;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 购物车商品
 */
@TableName(value = "cart_item", autoResultMap = true)
public class CartItem extends BaseModel<CartItem> implements CartItemDetails, Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 商品系列id
     */
    @TableField("spu_id")
    private Long spuId;
    /**
     * 规格商品id
     */
    @TableField("commodity_id")
    private Long commodityId;
    /**
     * 所属用户
     */
    @TableField("user_id")
    private String userId;
    /**
     * 商品名称
     */
    private String product;
    /**
     * 标题
     */
    private String title;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 加入时官方价格
     */
    @TableField("previous_official_price")
    private String previousOfficialPrice;
    /**
     * 加入时市场价格
     */
    @TableField("previous_market_price")
    private String previousMarketPrice;
    /**
     * 加入时会员价格
     */
    @TableField("previous_vip_price")
    private String previousVipPrice;
    /**
     * 规格（json）
     */
    @TableField("sku_attributes")
    private String skuAttributes;
    /**
     * 商品主图
     */
    @TableField("index_image")
    private String indexImage;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    @Override
    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPreviousOfficialPrice() {
        return previousOfficialPrice;
    }

    public void setPreviousOfficialPrice(String previousOfficialPrice) {
        this.previousOfficialPrice = previousOfficialPrice;
    }

    @Override
    public String getPreviousMarketPrice() {
        return previousMarketPrice;
    }

    public void setPreviousMarketPrice(String previousMarketPrice) {
        this.previousMarketPrice = previousMarketPrice;
    }

    public String getPreviousVipPrice() {
        return previousVipPrice;
    }

    public void setPreviousVipPrice(String previousVipPrice) {
        this.previousVipPrice = previousVipPrice;
    }

    @Override
    public String getSkuAttributes() {
        return skuAttributes;
    }

    public void setSkuAttributes(String skuAttributes) {
        this.skuAttributes = skuAttributes;
    }

    @Override
    public String getIndexImage() {
        return indexImage;
    }

    public void setIndexImage(String indexImage) {
        this.indexImage = indexImage;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", commodityId=" + commodityId +
                ", userId='" + userId + '\'' +
                ", product='" + product + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", quantity=" + quantity +
                ", previousOfficialPrice='" + previousOfficialPrice + '\'' +
                ", previousMarketPrice='" + previousMarketPrice + '\'' +
                ", previousVipPrice='" + previousVipPrice + '\'' +
                ", skuAttributes='" + skuAttributes + '\'' +
                ", indexImage='" + indexImage + '\'' +
                '}';
    }
}
