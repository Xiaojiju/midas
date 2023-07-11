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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.entity.BrandCategoryRelation;
import com.mtfm.purchase.manager.BrandRelationManager;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.mapper.BrandCategoryRelationMapper;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.service.bo.BrandPageQuery;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public void setRelations(Long brandId, Collection<Long> categories) {
        if (brandId == null) {
            throw new NullPointerException("brand id should not be null");
        }
        if (CollectionUtils.isEmpty(categories)) {
            return ;
        }
        // 校验每个分类是否都存在，仅添加已存在的分类
        List<Long> categoryIds = this.baseMapper.selectExist(categories);
        // 仅仅允许添加最低一级分类，如果包含不存在的分类或者不是最低一级的分类，默认不进行处理；
        if (CollectionUtils.isEmpty(categoryIds)) {
            return ;
        }
        // 比对已存在的分类，进行添加没有关联的分类
        this.removeRelations(brandId, null);
        List<BrandCategoryRelation> relations = categoryIds.stream().map(item -> {
            BrandCategoryRelation relation = new BrandCategoryRelation();
            relation.setBrandId(brandId);
            relation.setCategoryId(item);
            return relation;
        }).collect(Collectors.toList());
        this.saveBatch(relations);
    }

    @Override
    public void removeRelations(Long brandId, Collection<Long> categories) {
        if (brandId == null) {
            throw new NullPointerException("brand id should not be null");
        }
        // 如果分类集合为空，则删除所有
        QueryWrapper<BrandCategoryRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BrandCategoryRelation::getBrandId, brandId)
                .in(!CollectionUtils.isEmpty(categories), BrandCategoryRelation::getCategoryId, categories);
        this.remove(queryWrapper);
    }

    @Override
    public List<CategoryDetails> loadCategoriesByBrand(Long brandId) {
        return this.baseMapper.selectCategoryByBrandId(brandId);
    }

    @Override
    public PageTemplate<BrandDetails> loadPage(BrandPageQuery query) {
        Page<BrandDetails> page = Page.of(query.getCurrent(), query.getSize());
        page = this.baseMapper.selectBrandPage(page, query);
        return new PageTemplate<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    protected CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
}
