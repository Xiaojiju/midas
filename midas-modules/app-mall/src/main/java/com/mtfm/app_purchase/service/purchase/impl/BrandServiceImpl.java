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

import com.mtfm.app_purchase.service.purchase.BrandService;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.BrandManager;
import com.mtfm.purchase.manager.BrandRelationManager;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.service.bo.BrandPageQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌管理业务
 */
@Service
public class BrandServiceImpl implements BrandService {

    private final BrandManager brandManager;

    private final BrandRelationManager brandRelationManager;

    public BrandServiceImpl(BrandManager brandManager, BrandRelationManager brandRelationManager) {
        this.brandManager = brandManager;
        this.brandRelationManager = brandRelationManager;
    }

    @Override
    public BrandDetails getBrand(long id) {
        return this.brandManager.loadBrandById(id);
    }

    @Override
    public PageTemplate<BrandDetails> getPage(BrandPageQuery query) {
        return this.brandRelationManager.loadPage(query);
    }

    @Override
    public List<CategoryDetails> getCategories(long brand) {
        return this.brandRelationManager.loadCategoriesByBrand(brand);
    }
}
