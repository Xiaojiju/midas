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
import com.mtfm.core.util.validator.ValidateGroup;
import com.mtfm.purchase.entity.Category;
import com.mtfm.tools.enums.Judge;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类详情
 */
public class CategoryDetails implements BeanConverter<Category> {

    @NotNull(groups = ValidateGroup.Update.class, message = "Service.nullId")
    private Long id;

    @NotBlank(groups = ValidateGroup.Create.class, message = "CategoryDetails.blankCategory")
    private String category;

    private Long parentId;

    private Integer level;

    private Judge display;

    private String icon;

    public CategoryDetails() {
    }

    public CategoryDetails(Long id, String category, Long parentId, Integer level, Judge display, String icon) {
        this.id = id;
        this.category = category;
        this.parentId = parentId;
        this.level = level;
        this.display = display;
        this.icon = icon;
    }

    @Override
    public Category convertTo() {
        Category bean = new Category();
        bean.setId(this.id);
        bean.setCategory(this.category);
        bean.setParentId(this.parentId);
        bean.setLevel(this.level);
        bean.setDisplay(this.display);
        bean.setIcon(this.icon);
        return bean;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Judge getDisplay() {
        return display;
    }

    public void setDisplay(Judge display) {
        this.display = display;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}