package com.mtfm.purchase.manager.service.bo;

import com.mtfm.core.util.page.PageQuery;
import com.mtfm.tools.enums.Judge;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品系列列表查询参数
 */
public class SplitPageQuery extends PageQuery implements Serializable {
    /**
     * 商品名
     */
    private String product;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 上架
     */
    private Judge listing;
    /**
     * 小于时间
     */
    private LocalDateTime lessDateTime;
    /**
     * 大于时间
     */
    private LocalDateTime greaterDateTime;

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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public LocalDateTime getLessDateTime() {
        return lessDateTime;
    }

    public void setLessDateTime(LocalDateTime lessDateTime) {
        this.lessDateTime = lessDateTime;
    }

    public LocalDateTime getGreaterDateTime() {
        return greaterDateTime;
    }

    public void setGreaterDateTime(LocalDateTime greaterDateTime) {
        this.greaterDateTime = greaterDateTime;
    }

    public Judge getListing() {
        return listing;
    }

    public void setListing(Judge listing) {
        this.listing = listing;
    }
}
