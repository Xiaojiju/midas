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

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品sku
 */
@TableName(value = "purchase_sku", autoResultMap = true)
public class StockKeepingUnit extends BaseModel<StandardProductUnit> {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 关联商品
     */
    @TableField("spu_id")
    private Long spuId;
    /**
     * 名称
     */
    @TableField("sku_name")
    private String skuName;
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
     * 默认图片
     */
    @TableField("index_image")
    private String indexImage;
    /**
     * 标题
     */
    private String title;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 官方价格
     */
    private String price;
    /**
     * 销量
     */
    private Integer sale;
    /**
     * 库存
     * 注意：
     *   1. 如果设计需要秒杀场景，不应该每次都去请求来进行修改该属性，应该咋铁定的秒杀数量，在开始活动时，直接扣掉响应的数量，然后在缓存中进行操作，
     *   然后异步通知各个用户；
     *   2. 相对于第1点的场景，在高并发的场景下也可以这样处理，进行分段式扣除，来降低数据库的压力，也保证该属性的一致性；
     */
    private Integer stocks;

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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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
}
