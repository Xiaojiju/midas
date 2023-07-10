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

import com.mtfm.core.PropertyEntity;
import com.mtfm.core.convert.BeanConverter;
import com.mtfm.purchase.entity.Category;
import com.mtfm.tools.enums.Judge;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类详情
 */
public class CategoryDetails implements PropertyEntity<Long>, BeanConverter<Category> {
    /**
     * id
     */
    private Long target;

    private String category;

    private Long parentId;

    private Integer level;

    private Judge show;

    private String icon;

    @Override
    public Category convertTo() {
        Category bean = new Category();
        bean.setId(target);
        bean.setCategory(this.category);
        bean.setParentId(this.parentId);
        bean.setLevel(this.level);
        bean.setShow(this.show);
        bean.setIcon(this.icon);
        return bean;
    }

    @Override
    public Long getTarget() {
        return this.target;
    }

    public void setTarget(Long target) {
        this.target = target;
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

    public Judge getShow() {
        return show;
    }

    public void setShow(Judge show) {
        this.show = show;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
