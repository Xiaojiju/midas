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
package com.mtfm.app_purchase;

import com.mtfm.purchase.manager.BrandManager;
import com.mtfm.purchase.manager.BrandRelationManager;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.service.BrandDetailsService;
import com.mtfm.purchase.manager.service.BrandRelationService;
import com.mtfm.purchase.manager.service.CategoryDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 加载业务bean
 */
@Configuration
public class AppMallConfiguration {

    @Bean
    public CategoryManager categoryManager() {
        return new CategoryDetailsService();
    }

    @Bean
    public BrandRelationManager brandRelationManager(CategoryManager categoryManager) {
        return new BrandRelationService(categoryManager);
    }

    @Bean
    public BrandManager brandManager(BrandRelationManager brandRelationManager) {
        return new BrandDetailsService(brandRelationManager);
    }
}
