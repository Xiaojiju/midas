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
package com.mtfm.purchase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mtfm.datasource.BaseModel;
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品SPU
 */
@TableName(value = "purchase_spu", autoResultMap = true)
public class StandardProductUnit extends BaseModel<StandardProductUnit> {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 产品名称
     */
    private String product;
    /**
     * 所属分类
     */
    @TableField("category_id")
    private Long categoryId;
    /**
     * 所属品牌
     */
    @TableField("brand_id")
    private Long brandId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 是否上架
     */
    @TableField(value = "listing", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge listing;
    /**
     * 简要说明
     */
    private String brief;

    public StandardProductUnit() {
    }

    public StandardProductUnit(Long id, String product, Long categoryId, Long brandId, String unit, Judge listing, String brief) {
        this.id = id;
        this.product = product;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.unit = unit;
        this.listing = listing;
        this.brief = brief;
    }

    public static SpuBuilder created(Long id) {
        return new SpuBuilder(id);
    }

    public static SpuBuilder uncreated() {
        return new SpuBuilder();
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static class SpuBuilder {

        private final Long id;
        /**
         * 产品名称
         */
        private String product;
        /**
         * 所属分类
         */
        private Long categoryId;
        /**
         * 所属品牌
         */
        private Long brandId;
        /**
         * 单位
         */
        private String unit;
        /**
         * 是否上架
         */
        private Judge listing;
        /**
         * 简要说明
         */
        private String brief;

        private SpuBuilder() {
            this(null);
        }

        private SpuBuilder(Long id) {
            this.id = id;
        }

        public SpuBuilder withProductName(String product) {
            this.product = product;
            return this;
        }

        public SpuBuilder withCategoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public SpuBuilder withBrandId(Long brandId) {
            this.brandId = brandId;
            return this;
        }

        public SpuBuilder withUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public SpuBuilder listing(Judge listing) {
            this.listing = listing;
            return this;
        }

        public SpuBuilder writeBrief(String brief) {
            this.brief = brief;
            return this;
        }

        public StandardProductUnit build() {
            return new StandardProductUnit(this.id, this.product, this.categoryId, this.brandId, this.unit,
                    this.listing, this.brief);
        }
    }
}
