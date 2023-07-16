package com.mtfm.purchase.manager.service.bo;

import com.mtfm.core.util.page.PageQuery;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 规格商品查询条件
 */
public class CommodityPageQuery extends PageQuery {
    /**
     * 产品名称
     */
    private String product;
    /**
     * 分类id
     */
    private Long category;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 销量排序
     */
    private String sortBySale;
    /**
     * 价格排序
     */
    private String sortByPrice;
    /**
     * 小于价格
     */
    private Integer lessPrice;
    /**
     * 大于价格
     */
    private Integer greaterPrice;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSortBySale() {
        return sortBySale;
    }

    public void setSortBySale(String sortBySale) {
        this.sortBySale = sortBySale;
    }

    public String getSortByPrice() {
        return sortByPrice;
    }

    public void setSortByPrice(String sortByPrice) {
        this.sortByPrice = sortByPrice;
    }

    public Integer getLessPrice() {
        return lessPrice;
    }

    public void setLessPrice(Integer lessPrice) {
        this.lessPrice = lessPrice;
    }

    public Integer getGreaterPrice() {
        return greaterPrice;
    }

    public void setGreaterPrice(Integer greaterPrice) {
        this.greaterPrice = greaterPrice;
    }
}
