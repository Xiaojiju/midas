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

import com.mtfm.purchase.entity.CommodityImage;

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

    private Long id;

    private Long spuId;

    private String commodityName;

    private String weight;

    private String indexImage;

    private String title;

    private String subtitle;

    private String price;

    private Integer sale;

    private Integer stocks;

    private Spu spu;

    private List<Spu.SkuVal> skuVals;

    private Sku sku;

    /**
     * 商品独有属性，即对应规格下的商品属性
     */
    private List<AttributeGroupValue> commodityAttributes;
    /**
     * 规格图片
     */
    private List<CommodityImage> skuImages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIndexImage() {
        return indexImage;
    }

    public void setIndexImage(String indexImage) {
        this.indexImage = indexImage;
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

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Spu.SkuVal> getSkuVals() {
        return skuVals;
    }

    public void setSkuVals(List<Spu.SkuVal> skuVals) {
        this.skuVals = skuVals;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public List<AttributeGroupValue> getCommodityAttributes() {
        return commodityAttributes;
    }

    public void setCommodityAttributes(List<AttributeGroupValue> commodityAttributes) {
        this.commodityAttributes = commodityAttributes;
    }

    public List<CommodityImage> getSkuImages() {
        return skuImages;
    }

    public void setSkuImages(List<CommodityImage> skuImages) {
        this.skuImages = skuImages;
    }
}
