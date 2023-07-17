package com.mtfm.backend_support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BackendSupportConfiguration {
    private final ImportBackendFilter importBackendFilter;

    public BackendSupportConfiguration(ResourceBundleMessageSource messageSource,
                                       ImportBackendFilter importBackendFilter) {
        messageSource.addBasenames("i18n/backend_support_messages");
        this.importBackendFilter = importBackendFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.logout().disable()
                .csrf().disable()
//                .addFilterAfter(importBackendFilter.requestBodyAuthenticationProcessingFilter(), LogoutFilter.class)
//                .addFilterAfter(importBackendFilter.tokenResolutionProcessingFilter(),
//                        RequestBodyAuthenticationProcessingFilter.class)
//                .addFilterAfter(importBackendFilter.requestBodyLogoutFilter(), LogoutFilter.class)
                .sessionManagement().disable();
        return security.build();
    }

}
