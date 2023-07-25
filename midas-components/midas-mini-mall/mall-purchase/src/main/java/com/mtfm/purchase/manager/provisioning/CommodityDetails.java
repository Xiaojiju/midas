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
package com.mtfm.purchase.manager.provisioning;

import com.mtfm.core.util.validator.ValidateGroup;
import com.mtfm.purchase.entity.CommodityAttribute;
import com.mtfm.purchase.entity.CommodityImage;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品详细信息
 * 其中包含：spu信息、规格sku
 * 该实体为闯将商品的第二步实体
 */
public class CommodityDetails implements Serializable {

    @NotNull(groups = ValidateGroup.Update.class, message = "Service.nullId")
    private Long id;

    @NotNull(groups = ValidateGroup.Create.class, message = "CommodityDetails.spuId")
    private Long spuId;

    private String commodityCode;

    @NotBlank(groups = ValidateGroup.Create.class, message = "CommodityDetails.commodityName")
    private String commodityName;

    @NotBlank(groups = ValidateGroup.Create.class, message = "CommodityDetails.weight")
    private String weight;

    @NotBlank(groups = ValidateGroup.Create.class, message = "CommodityDetails.title")
    private String title;

    private String subtitle;

    private String price;

    @NotBlank(groups = ValidateGroup.Create.class, message = "CommodityDetails.currentPrice")
    private String currentPrice;

    private String vipPrice;

    private Integer sale;

    @NotNull(groups = ValidateGroup.Create.class, message = "CommodityDetails.stocks")
    private Integer stocks;
    /**
     * 对应选择的规则
     */
    private List<SpuDetails.SkuVal> skuVals;

    /**
     * 商品独有属性，即对应规格下的商品属性
     */
    private List<CommodityAttribute> commodityAttributes;
    /**
     * 规格图片
     */
    @Size(max = 9, groups = {ValidateGroup.Create.class, ValidateGroup.Update.class}, message = "SpuDetails.imageLimit")
    private List<CommodityImage> skuImages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getStocks() {
        return stocks;
    }

    public void setStocks(Integer stocks) {
        this.stocks = stocks;
    }

    public List<SpuDetails.SkuVal> getSkuVals() {
        return skuVals;
    }

    public void setSkuVals(List<SpuDetails.SkuVal> skuVals) {
        this.skuVals = skuVals;
    }

    public List<CommodityAttribute> getCommodityAttributes() {
        return commodityAttributes;
    }

    public void setCommodityAttributes(List<CommodityAttribute> commodityAttributes) {
        this.commodityAttributes = commodityAttributes;
    }

    public List<CommodityImage> getSkuImages() {
        return skuImages;
    }

    public void setSkuImages(List<CommodityImage> skuImages) {
        this.skuImages = skuImages;
    }
}
