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
 * 商品图片
 */
@TableName(value = "purchase_spu_image", autoResultMap = true)
public class SpuImage implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 关联商品id
     */
    @TableField("spu_id")
    private Long spuId;
    /**
     * 图片名称
     */
    @TableField("image_name")
    private String imageName;
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
     * 是否为首张图片（默认），仅能有一张
     */
    @TableField(value = "index_image", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge indexImage;

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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
