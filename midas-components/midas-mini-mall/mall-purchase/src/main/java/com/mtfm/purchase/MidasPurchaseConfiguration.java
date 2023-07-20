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
package com.mtfm.purchase;

import com.mtfm.purchase.manager.*;
import com.mtfm.purchase.manager.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 必须将国际化文件加入在messageSource中，否则在引入{@link org.springframework.context.MessageSourceAware}后，可能导致加载不到模块的
 * 国际化文件；当然这是在spring bean中的场景下，如果在静态文件中需要使用国际化，可以使用{@link PurchaseMessageSource#getAccessor()}来进
 * 行获取{@link org.springframework.context.support.MessageSourceAccessor};
 */
@Configuration
public class MidasPurchaseConfiguration {

    public MidasPurchaseConfiguration(ResourceBundleMessageSource messageSource) {
        // 添加国际化文件
        messageSource.addBasenames("i18n/purchase_messages");
    }

    @Bean
    public SpuAttributeManageService spuAttributeManageService() {
        return new SpuAttributeManageService();
    }

    @Bean
    public CommodityAttributeManageService commodityAttributeManageService() {
        return new CommodityAttributeManageService();
    }

    @Bean
    public CategoryManager categoryManager() {
        return new CategoryDetailsService();
    }

    @Bean
    public SpuImageService spuImageService() {
        return new SpuImageService();
    }

    @Bean
    public CommodityImageService commodityImageService() {
        return new CommodityImageService();
    }

    @Bean
    public SkuItemValueManager skuItemValueManager() {
        return new SkuItemValueService();
    }

    @Bean
    public SkuManager skuManager(SkuItemValueManager skuItemValueManager) {
        return new SkuManageService(skuItemValueManager);
    }

    @Bean
    public SpuManager spuManager(SpuImageService spuImageService,
                                 SpuAttributeManageService spuAttributeManageService,
                                 SkuManager skuManager,
                                 CommodityManager commodityManager) {
        return new SpuDetailsService(spuImageService, spuAttributeManageService, skuManager, commodityManager);
    }

    @Bean
    public CommoditySkuRelationManager commoditySkuRelationManager(SkuManager skuManager) {
        return new CommoditySkuRelationService(skuManager);
    }

    @Bean
    public CommodityManager commodityManager(CommodityAttributeManageService commodityAttributeManageService,
                                             CommodityImageService commodityImageService,
                                             CommoditySkuRelationManager commoditySkuRelationManager) {
        return new CommodityManageService(commodityAttributeManageService, commodityImageService, commoditySkuRelationManager);
    }

    @Bean
    public TagManager tagManager() {
        return new TagManageService();
    }
}
