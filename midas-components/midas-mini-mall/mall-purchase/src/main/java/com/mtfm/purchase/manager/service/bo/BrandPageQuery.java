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
package com.mtfm.purchase.manager.service.bo;

import com.mtfm.core.util.page.PageQuery;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌查询参数
 */
public class BrandPageQuery extends PageQuery {
    /**
     * 品牌名称
     */
    private String brand;
    /**
     * 品牌首字母
     */
    private String letter;
    /**
     * 分类名称
     */
    private Long categoryId;
    /**
     * 通过首字母排序
     */
    private String sortByLetter;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSortByLetter() {
        return sortByLetter;
    }

    public void setSortByLetter(String sortByLetter) {
        this.sortByLetter = sortByLetter;
    }
}
