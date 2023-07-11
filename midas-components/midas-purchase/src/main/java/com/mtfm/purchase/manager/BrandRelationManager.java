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
package com.mtfm.purchase.manager;

import com.mtfm.core.util.page.Page;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.entity.Brand;
import com.mtfm.purchase.entity.Category;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.service.bo.BrandPageQuery;

import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌分类管理操作定义
 */
public interface BrandRelationManager {
    /**
     * 添加关系
     * @param brandId 品牌id
     * @param categories 分类id
     */
    void setRelations(Long brandId, Collection<Long> categories);

    /**
     * 删除关系
     * @param brandId 品牌id
     * @param categories 分类
     */
    void removeRelations(Long brandId, Collection<Long> categories);

    /**
     * 获取关联品牌的分类
     * @param brandId 品牌id
     * @return 分类集合
     */
    List<CategoryDetails> loadCategoriesByBrand(Long brandId);

    /**
     * 分页查询
     * @param query 过滤条件
     * @return 品牌详情
     */
    PageTemplate<BrandDetails> loadPage(BrandPageQuery query);
}
