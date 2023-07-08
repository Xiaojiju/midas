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
     * 重量（克）
     */
    @TableField(value = "weight", jdbcType = JdbcType.DECIMAL)
    private String weight;
    /**
     * 单位
     */
    private String unit;
    /**
     * 是否上架
     */
    @TableField(value = "grounding", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge grounding;
    /**
     * 简要说明
     */
    private String brief;

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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Judge getGrounding() {
        return grounding;
    }

    public void setGrounding(Judge grounding) {
        this.grounding = grounding;
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
}
