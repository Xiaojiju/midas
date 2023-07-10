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
package com.mtfm.purchase.manager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.purchase.entity.Brand;
import com.mtfm.purchase.entity.BrandCategoryRelation;
import com.mtfm.purchase.entity.Category;
import com.mtfm.purchase.manager.BrandRelationManager;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.mapper.BrandCategoryRelationMapper;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌分类关系管理
 */
public class BrandRelationService extends ServiceImpl<BrandCategoryRelationMapper, BrandCategoryRelation> implements BrandRelationManager {

    private CategoryManager categoryManager;

    public BrandRelationService(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Override
    public void addRelations(Long brandId, Collection<Long> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return ;
        }
        // 校验每个分类是否都存在，仅添加已存在的分类

        // 比对已存在的分类，进行添加没有关联的分类
    }

    @Override
    public void removeRelations(Long brandId, Collection<Long> categories) {

    }

    @Override
    public Collection<Brand> loadBrandByCategory(Long id, String category) {
        return null;
    }

    @Override
    public Collection<Category> loadCategoriesByBrand(Long id, String brand) {
        return null;
    }

    protected CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
}
