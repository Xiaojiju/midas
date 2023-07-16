package com.mtfm.purchase.manager.provisioning;

import com.mtfm.tools.enums.Judge;

import java.io.Serializable;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 列表简要信息
 */
public class CommoditySplitDetails implements Serializable {

    private Long id;

    private String product;
    /**
     * 所属分类
     */
    private String category;
    /**
     * 所属品牌
     */
    private String brand;
    /**
     * 单位
     */
    private String unit;
    /**
     * 是否上架
     */
    private Judge listing;
    /**
     * 库存
     */
    private List<Sku> items;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
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

    public List<Sku> getItems() {
        return items;
    }

    public void setItems(List<Sku> items) {
        this.items = items;
    }
}
