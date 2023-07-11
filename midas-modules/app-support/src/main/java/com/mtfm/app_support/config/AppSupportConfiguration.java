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
package com.mtfm.app_support.config;

import com.mtfm.security.filter.RequestBodyLogoutFilter;
import com.mtfm.wechat_mp.filter.MiniProgramAuthenticationProcessingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 前台过滤器链路
 */
@Configuration
public class AppSupportConfiguration {

    private final ImportAppFilter importAppFilter;

    public AppSupportConfiguration(ImportAppFilter importAppFilter, ResourceBundleMessageSource resourceBundleMessageSource) {
        this.importAppFilter = importAppFilter;
        resourceBundleMessageSource.addBasenames("i18n/app_support_messages");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.logout().disable()
                .csrf().disable()
                .addFilterBefore(importAppFilter.requestBodyLogoutFilter(), LogoutFilter.class)
                .addFilterBefore(importAppFilter.miniProgramAuthenticationProcessingFilter(), RequestBodyLogoutFilter.class)
//                .addFilterBefore(importAppFilter.tokenResolutionProcessingFilter(),
//                        MiniProgramAuthenticationProcessingFilter.class)
                .sessionManagement().disable();
        return security.build();
    }
    
}
