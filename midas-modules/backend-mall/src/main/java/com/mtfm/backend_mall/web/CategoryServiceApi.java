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
package com.mtfm.backend_mall.web;

import com.mtfm.backend_mall.service.purchase.CategoryManageService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.provisioning.CategoryTree;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类业务api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class CategoryServiceApi {

    private CategoryManageService categoryManageService;

    public CategoryServiceApi(CategoryManageService categoryManageService) {
        this.categoryManageService = categoryManageService;
    }

    @GetMapping("/category/tree")
    public RestResult<List<CategoryTree>> pullTree() {
        return RestResult.success(this.categoryManageService.getTree());
    }

    @PostMapping("/category")
    public RestResult<Void> createCategory(@RequestBody CategoryDetails categoryDetails) {
        this.categoryManageService.createCategory(categoryDetails);
        return RestResult.success();
    }

    @PutMapping("/category")
    public RestResult<Void> updateCategory(@RequestBody CategoryDetails categoryDetails) {
        this.categoryManageService.updateCategory(categoryDetails);
        return RestResult.success();
    }

    @GetMapping("/category/{categoryId}")
    public RestResult<CategoryDetails> getCategoryDetails(@PathVariable("categoryId") long categoryId) {
        return RestResult.success(this.categoryManageService.getDetails(categoryId));
    }

    public CategoryManageService getCategoryManageService() {
        return categoryManageService;
    }

    public void setCategoryManageService(CategoryManageService categoryManageService) {
        this.categoryManageService = categoryManageService;
    }
}
