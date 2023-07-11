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
package com.mtfm.app_purchase.service.purchase;

import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.service.bo.BrandPageQuery;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.provisioning.BrandDetails;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌业务类
 */
public interface BrandService {
    /**
     * 获取品牌详情
     * @param id 品牌id
     * @return 品牌详情
     */
    BrandDetails getBrand(long id);

    /**
     * 根据过滤条件查询品牌分页
     * @param query 过滤条件
     * @return 品牌分页
     */
    PageTemplate<BrandDetails> getPage(BrandPageQuery query);

    /**
     * 获取品牌的分类
     * @param brand 品牌id
     * @return 分类详情
     */
    List<CategoryDetails> getCategories(long brand);

}
