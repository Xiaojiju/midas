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

import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.provisioning.CategoryTree;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类管理
 */
public interface CategoryManager {
    /**
     * 通过id获取分类详情
     * @param id 分类id
     * @return 分类详情
     */
    CategoryDetails loadCategoryById(Long id);

    /**
     * 通过分类名称获取分类详情
     * @param category 分类名
     * @return 分类详情
     */
    CategoryDetails loadCategoryByName(String category);

    /**
     * 创建一个分类
     * @param details 分类详情
     */
    void createCategory(CategoryDetails details) throws PurchaseExistException;

    /**
     * 修改一个分类
     * @param details 分类详情
     */
    void updateCategory(CategoryDetails details) throws PurchaseExistException;

    /**
     * 加载分类树
     * @return 分类树数组
     */
    List<CategoryTree> loadTree();

    /**
     * 获取指定id的分类树
     * @param id 分类id
     * @return 分类树
     */
    CategoryTree loadTreeById(Long id);

    /**
     * 获取指定分类名的分类树
     * @param category 分类名
     * @return 分类树
     */
    CategoryTree loadTreeByName(String category);

    /**
     * 通过id删除，如果有子节点则不允许删除
     * @param id 分类id
     */
    void removeCategoryById(Long id) throws PurchaseExistException;

    /**
     * 通过分类名删除，如果有子节点不允许删除
     * @param category 分类名
     */
    void removeCategoryByName(String category) throws PurchaseExistException;

    /**
     * 是否存在重名分类
     * @param category 分类名称
     * @return true 存在 false 不存在
     */
    boolean categoryExist(String category);
}
