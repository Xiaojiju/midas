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
package com.mtfm.app_purchase.service.purchase.impl;

import com.mtfm.app_purchase.service.purchase.CategoryService;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.provisioning.CategoryTree;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类业务
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryManager categoryManager;

    public CategoryServiceImpl(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Override
    public List<CategoryTree> getTree() {
        return this.categoryManager.loadTree();
    }

    protected CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
}
