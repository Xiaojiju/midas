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
package com.mtfm.purchase.manager.provisioning;

import com.mtfm.core.convert.BeanConverter;
import com.mtfm.purchase.entity.Brand;
import com.mtfm.tools.enums.Judge;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌详情
 */
public class BrandDetails implements BeanConverter<Brand>, Serializable {

    private Long id;

    private String brand;

    private String logo;

    private String description;

    private Judge show;

    private String indexLetter;

    private Integer sort;

    private List<CategoryDetails> categories;

    @Override
    public Brand convertTo() {
        return Brand.created(this.id, this.brand)
                .withLogo(this.logo)
                .describe(this.description)
                .show(this.show == null || this.show == Judge.YES)
                .sort(this.sort)
                .build();
    }

    public boolean hasCategory() {
        return !CollectionUtils.isEmpty(this.categories);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CategoryDetails> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDetails> categories) {
        this.categories = categories;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Judge getShow() {
        return show;
    }

    public void setShow(Judge show) {
        this.show = show;
    }

    public String getIndexLetter() {
        return indexLetter;
    }

    public void setIndexLetter(String indexLetter) {
        this.indexLetter = indexLetter;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
