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
package com.mtfm.app_purchase.service.provisioning;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mtfm.cart.manager.provisioning.CartItemDetails;
import com.mtfm.core.convert.LocalDateTimeDeserializer;
import com.mtfm.core.util.TimeConstants;
import com.mtfm.purchase.manager.provisioning.Sku;
import com.mtfm.tools.enums.Judge;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 购物车展示
 */
public class CartItemView implements CartItemDetails, Serializable {

    private Long id;
    /**
     * 商品系列id
     */
    private Long spuId;
    /**
     * 规格商品id
     */
    private Long commodityId;
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
    private String previousOfficialPrice;
    /**
     * 加入时市场价格
     */
    private String previousMarketPrice;
    /**
     * 加入时会员价格
     */
    private String previousVipPrice;
    /**
     * 规格（json）
     */
    private String skuAttributes;
    /**
     * 商品主图
     */
    private String indexImage;
    /**
     * 上架状态
     */
    private Judge listing;
    /**
     * 促销活动
     */
    private String promotion;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = TimeConstants.Y_M_D_H_M_S)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
    /**
     * 库存
     */
    private Sku sku;

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

    public Judge getListing() {
        return listing;
    }

    public void setListing(Judge listing) {
        this.listing = listing;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @Override
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }
}
