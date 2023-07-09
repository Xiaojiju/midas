package com.mtfm.backend_mall;

import com.mtfm.purchase.manager.BrandManager;
import com.mtfm.purchase.manager.BrandRelationManager;
import com.mtfm.purchase.manager.service.BrandDetailsService;
import com.mtfm.purchase.manager.service.BrandRelationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class backendMallConfiguration {

    @Bean
    public BrandRelationManager brandRelationManager() {
        return new BrandRelationService();
    }

    @Bean
    public BrandManager brandManager(BrandRelationManager brandRelationManager) {
        return new BrandDetailsService(brandRelationManager);
    }
}
