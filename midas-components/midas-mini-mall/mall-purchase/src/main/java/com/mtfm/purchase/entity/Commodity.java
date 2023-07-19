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
import org.apache.ibatis.type.JdbcType;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品sku
 */
@TableName(value = "purchase_commodity", autoResultMap = true)
public class Commodity extends BaseModel<StandardProductUnit> {
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
    @TableField("commodity_name")
    private String commodityName;
    /**
     * 重量（克）
     */
    @TableField(value = "weight", jdbcType = JdbcType.DECIMAL)
    private String weight;
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
     * 市场价
     */
    private String currentPrice;
    /**
     * 会员价
     */
    private String vipPrice;
    /**
     * 销量
     * 注意：
     *   1. 尽量采用缓存的方式，定时进行或其他策略来更新，频繁的更新该字段，会增加数据库压力；
     *   2. 要制定如果缓存出现问题后，销量的统计补救问题
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

    public Commodity() {
    }

    public Commodity(Long id, Long spuId, String commodityName, String weight, String title, String subtitle,
                     String price, String currentPrice, String vipPrice, Integer sale, Integer stocks) {
        this.id = id;
        this.spuId = spuId;
        this.commodityName = commodityName;
        this.weight = weight;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.currentPrice = currentPrice;
        this.vipPrice = vipPrice;
        this.sale = sale;
        this.stocks = stocks;
    }

    public static CommodityBuilder createdBuilder(long commodityId, long spuId) {
        return new CommodityBuilder(commodityId, spuId);
    }

    public static CommodityBuilder uncreatedBuilder(long spuId) {
        return new CommodityBuilder(spuId);
    }

    public static CommodityBuilder builder(Commodity commodity) {
        return null;
    }

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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public static class CommodityBuilder {

        private final Long id;

        private final Long spuId;

        private String commodityName;

        private String weight;

        private String title;

        private String subtitle;

        private String price;

        private String currentPrice;

        private String vipPrice;

        private Integer sale;

        private Integer stocks;

        private CommodityBuilder(Long spuId) {
            this(null, spuId);
        }

        private CommodityBuilder(Long id, Long spuId) {
            this.id = id;
            this.spuId = spuId;
        }

        public CommodityBuilder(Commodity commodity) {
            this.id = commodity.id;
            this.spuId = commodity.spuId;
            this.commodityName = commodity.commodityName;
            this.weight = commodity.weight;
            this.title = commodity.title;
            this.subtitle = commodity.subtitle;
            this.price = commodity.price;
            this.sale = commodity.sale;
            this.stocks = commodity.stocks;
            this.currentPrice = commodity.currentPrice;
            this.vipPrice = commodity.vipPrice;
        }

        public CommodityBuilder setName(String commodityName) {
            this.commodityName = commodityName;
            return this;
        }

        public CommodityBuilder setWeight(String weight) {
            this.weight = weight;
            return this;
        }

        public CommodityBuilder setTitle(String title, String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
            return this;
        }

        public CommodityBuilder setPrice(String price, String currentPrice, String vipPrice) {
            this.price = price;
            this.currentPrice = currentPrice;
            this.vipPrice = vipPrice;
            return this;
        }

        public CommodityBuilder hadSale(Integer sale) {
            this.sale = sale;
            return this;
        }

        public CommodityBuilder listingStocks(Integer stocks) {
            this.stocks = stocks;
            return this;
        }

        public Commodity build() {
            return new Commodity(this.id, this.spuId, this.commodityName, this.weight, this.title, this.subtitle,
                    this.price, this.currentPrice, this.vipPrice, this.sale, this.stocks);
        }
    }
}
