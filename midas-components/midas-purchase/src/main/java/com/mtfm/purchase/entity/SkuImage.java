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
import com.mtfm.tools.enums.Judge;

import java.io.Serializable;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * sku图片
 */
@TableName(value = "purchase_sku_image", autoResultMap = true)
public class SkuImage implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 关联sku
     */
    @TableField("sku_id")
    private Long skuId;
    /**
     * 图片地址
     */
    @TableField("image_url")
    private String imageUrl;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 默认图片
     */
    @TableField("index_image")
    private Judge indexImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Judge getIndexImage() {
        return indexImage;
    }

    public void setIndexImage(Judge indexImage) {
        this.indexImage = indexImage;
    }
}
