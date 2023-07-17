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
package com.mtfm.backend_mall.service.purchase;

import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.provisioning.CategoryTree;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类管理业务
 */
public interface CategoryManageService {
    /**
     * 获取分类树
     * @return 树
     */
    List<CategoryTree> getTree();

    /**
     * 创建分类
     * @param categoryDetails 分类详情
     */
    void createCategory(CategoryDetails categoryDetails);

    /**
     * 更新分类
     * @param categoryDetails 分类详情
     */
    void updateCategory(CategoryDetails categoryDetails);

    /**
     * 删除分裂
     * @param categoryId 分类id
     */
    void removeCategory(long categoryId);

    /**
     * 获取分类详情
     * @param categoryId 分类id
     * @return 分类详情
     */
    CategoryDetails getDetails(long categoryId);
}
