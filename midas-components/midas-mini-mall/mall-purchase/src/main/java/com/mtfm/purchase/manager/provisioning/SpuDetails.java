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
import com.mtfm.purchase.entity.SpuAttribute;
import com.mtfm.purchase.entity.SpuImage;
import com.mtfm.tools.enums.Judge;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品spu基本信息
 * 该对象为创建商品的第一步的组合实体
 */
public class SpuDetails implements Serializable {

    @NotNull(groups = ValidateGroup.Update.class, message = "Service.nullId")
    private Long id;

    private String spuCode;
    /**
     * 产品名称
     */
    @NotBlank(groups = ValidateGroup.Create.class, message = "SpuDetails.productNotBlank")
    private String product;

    @NotBlank(groups = ValidateGroup.Create.class, message = "SpuDetails.categoryNonNull")
    private Long categoryId;

    private Long brandId;
    /**
     * 单位
     */
    @NotBlank(groups = ValidateGroup.Create.class, message = "SpuDetails.unitNotBlank")
    private String unit;
    /**
     * 是否上架
     */
    private Judge listing;
    /**
     * 简要说明
     */
    private String brief;

    /**
     * 图片
     */
    @Size(max = 9, groups = {ValidateGroup.Create.class, ValidateGroup.Update.class}, message = "SpuDetails.imageLimit")
    private List<SpuImage> images;
    /**
     * 属性值
     */
    private List<SpuAttribute> spuAttributes;
    /**
     * 包含规格
     */
    private List<SkuItemGroup> groups;
    /**
     * 库存
     */
    private List<Sku> skus;
    /**
     * 所属分类
     */
    private Sample category;
    /**
     * 所属品牌
     */
    private Sample brand;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Sample getCategory() {
        return category;
    }

    public void setCategory(Sample category) {
        this.category = category;
    }

    public Sample getBrand() {
        return brand;
    }

    public void setBrand(Sample brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Judge getListing() {
        return listing;
    }

    public void setListing(Judge listing) {
        this.listing = listing;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public List<SpuImage> getImages() {
        return images;
    }

    public void setImages(List<SpuImage> images) {
        this.images = images;
    }

    public List<SpuAttribute> getSpuAttributes() {
        return spuAttributes;
    }

    public void setSpuAttributes(List<SpuAttribute> spuAttributes) {
        this.spuAttributes = spuAttributes;
    }

    public List<SkuItemGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<SkuItemGroup> groups) {
        this.groups = groups;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    /**
     * @author  一块小饼干
     * @since 1.0.0
     * 规则分组
     */
    public static class SkuItemGroup implements Serializable {


        private Long skuId;

        private String itemName;

        private String icon;

        private List<SkuVal> skuValues;

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public List<SkuVal> getSkuValues() {
            return skuValues;
        }

        public void setSkuValues(List<SkuVal> skuValues) {
            this.skuValues = skuValues;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    /**
     * @author  一块小饼干
     * @since 1.0.0
     * 规则值
     */
    public static class SkuVal implements Serializable {

        private Long id;

        private String itemName;

        private String itemImage;

        private String itemValue;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemImage() {
            return itemImage;
        }

        public void setItemImage(String itemImage) {
            this.itemImage = itemImage;
        }

        public String getItemValue() {
            return itemValue;
        }

        public void setItemValue(String itemValue) {
            this.itemValue = itemValue;
        }
    }
}
