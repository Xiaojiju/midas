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

import com.mtfm.app_purchase.service.purchase.BrandService;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.service.bo.BrandPageQuery;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌api接口
 */
@RestController
@RequestMapping("/solar/api/v1")
public class BrandViewApi {

    private final BrandService brandService;

    public BrandViewApi(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * 根据id获取品牌详情
     * @param id 品牌id
     * @return 品牌详情
     */
    @GetMapping("/brand/{id}")
    public RestResult<BrandDetails> getBrand(@PathVariable("id") long id) {
        return RestResult.success(this.brandService.getBrand(id));
    }

    /**
     * 分页过滤
     * @param query 过滤参数
     * @return 分页结果
     */
    @GetMapping("/brands")
    public RestResult<PageTemplate<BrandDetails>> brandPage(BrandPageQuery query) {
        return RestResult.success(this.brandService.getPage(query));
    }

    /**
     * 获取品牌的分类
     * @param brandId 品牌id
     * @return 分类详情
     */
    @GetMapping("/brand/categories")
    public RestResult<List<CategoryDetails>> ofCategories(long brandId) {
        return RestResult.success(this.brandService.getCategories(brandId));
    }
}
