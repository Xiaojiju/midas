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
package com.mtfm.app_purchase.web.api;

import com.mtfm.app_purchase.service.purchase.CategoryService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.purchase.manager.provisioning.CategoryTree;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类信息api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class CategoryViewApi {

    private final CategoryService categoryService;

    public CategoryViewApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取不同模块的分类树
     * @param category 分类名 现在仅有 逛一逛、特选
     * @return 分类树
     */
    @GetMapping("/categories/tree")
    public RestResult<CategoryTree> pushTree(String category) {
        return RestResult.success(this.categoryService.getTree(category));
    }
}
