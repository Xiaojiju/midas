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
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品属性
 */
@TableName(value = "purchase_attr", autoResultMap = true)
public class Attribute implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 属性名
     */
    @TableField("attr_name")
    private String attrName;
    /**
     * 图标
     */
    private String icon;
    /**
     * 属性类型
     * 0 销售类型
     * 销售类型为商品的不同规格，例如颜色，一个商品可能分为多个颜色，即可变属性
     * 1 基本属性
     * 基本属性为商品的通用属性，即为不可变的属性，例如手机的cpu型号是不可变的，当然，iphone14和iPhone14Pro是两款产品
     */
    @TableField(value = "attr_type", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Integer attrType;
    /**
     * 是否可用
     */
    @TableField(value = "enable", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge enable;
    /**
     * 所属分类
     */
    @TableField("category_id")
    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getAttrType() {
        return attrType;
    }

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    public Judge getEnable() {
        return enable;
    }

    public void setEnable(Judge enable) {
        this.enable = enable;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
