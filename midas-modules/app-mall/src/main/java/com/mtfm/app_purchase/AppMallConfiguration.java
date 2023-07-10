package com.mtfm.app_purchase;

import com.mtfm.purchase.manager.BrandManager;
import com.mtfm.purchase.manager.BrandRelationManager;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.service.BrandDetailsService;
import com.mtfm.purchase.manager.service.BrandRelationService;
import com.mtfm.purchase.manager.service.CategoryDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
