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

import com.mtfm.app_purchase.service.express.DeliveryAddressService;
import com.mtfm.app_purchase.service.express.impl.DeliveryAddressServiceImpl;
import com.mtfm.app_purchase.service.purchase.CategoryService;
import com.mtfm.app_purchase.service.purchase.CommodityService;
import com.mtfm.app_purchase.service.purchase.impl.CategoryServiceImpl;
import com.mtfm.app_purchase.service.purchase.impl.CommodityServiceImpl;
import com.mtfm.express.manager.DeliveryAddressManager;
import com.mtfm.express.manager.ExpressRelationManager;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.CommodityManager;
import com.mtfm.purchase.manager.SpuManager;
import com.mtfm.purchase.manager.TagManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 加载业务bean
 */
@Configuration
public class AppMallConfiguration {

    public AppMallConfiguration(ResourceBundleMessageSource resourceBundleMessageSource) {
        resourceBundleMessageSource.addBasenames("i18n/app_mall_messages");
    }

    @Bean
    public CategoryService categoryService(CategoryManager categoryManager) {
        return new CategoryServiceImpl(categoryManager);
    }

    @Bean
    public CommodityService commodityService(SpuManager spuManager, CommodityManager commodityManager,
                                             TagManager tagManager, ExpressRelationManager expressRelationManager) {
        return new CommodityServiceImpl(commodityManager, spuManager, tagManager, expressRelationManager);
    }

    @Bean
    public DeliveryAddressService deliveryAddressService(DeliveryAddressManager deliveryAddressManager) {
        return new DeliveryAddressServiceImpl(deliveryAddressManager);
    }
}
