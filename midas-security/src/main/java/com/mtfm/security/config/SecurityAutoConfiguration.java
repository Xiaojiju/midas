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
package com.mtfm.security.config;

import com.mtfm.security.cache.RedisUserCache;
import com.mtfm.security.filter.RequestBodyAuthenticationProcessingFilter;
import com.mtfm.security.filter.RequestBodyLogoutFilter;
import com.mtfm.security.filter.TokenResolutionProcessingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 配置HttpSecurity
 * @author 一块小饼干
 * @since 1.0.0
 */
@Import({
        SecurityServiceAutoConfigure.class,
        WebSecurityConfig.class,
        WebAuthenticationConfig.class,
        WebSecurityProperties.class,
        RedisUserCache.class,
        WebAuthProperties.class
})
@Configuration
public class SecurityAutoConfiguration {

    private RequestBodyLogoutFilter requestBodyLogoutFilter;
    private RequestBodyAuthenticationProcessingFilter requestBodyAuthenticationProcessingFilter;
    private TokenResolutionProcessingFilter tokenResolutionProcessingFilter;

    public SecurityAutoConfiguration(RequestBodyLogoutFilter requestBodyLogoutFilter,
                                     RequestBodyAuthenticationProcessingFilter requestBodyAuthenticationProcessingFilter,
                                     TokenResolutionProcessingFilter tokenResolutionProcessingFilter,
                                     ResourceBundleMessageSource resourceBundleMessageSource) {
        this.requestBodyLogoutFilter = requestBodyLogoutFilter;
        this.requestBodyAuthenticationProcessingFilter = requestBodyAuthenticationProcessingFilter;
        this.tokenResolutionProcessingFilter = tokenResolutionProcessingFilter;
        resourceBundleMessageSource.addBasenames("org.springframework.security.messages");
        resourceBundleMessageSource.addBasenames("i18n/solar_messages");
    }

    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.logout().disable()
                .csrf().disable()
                .addFilterAfter(requestBodyAuthenticationProcessingFilter, LogoutFilter.class)
                .addFilterAfter(requestBodyLogoutFilter, LogoutFilter.class)
                .addFilterBefore(tokenResolutionProcessingFilter, RequestBodyAuthenticationProcessingFilter.class)
                .sessionManagement().disable();
        return security.build();
    }

    public RequestBodyLogoutFilter getRequestBodyLogoutFilter() {
        return requestBodyLogoutFilter;
    }

    public void setRequestBodyLogoutFilter(RequestBodyLogoutFilter requestBodyLogoutFilter) {
        this.requestBodyLogoutFilter = requestBodyLogoutFilter;
    }

    public RequestBodyAuthenticationProcessingFilter getRequestBodyAuthenticationProcessingFilter() {
        return requestBodyAuthenticationProcessingFilter;
    }

    public void setRequestBodyAuthenticationProcessingFilter(RequestBodyAuthenticationProcessingFilter requestBodyAuthenticationProcessingFilter) {
        this.requestBodyAuthenticationProcessingFilter = requestBodyAuthenticationProcessingFilter;
    }

    public TokenResolutionProcessingFilter getTokenResolutionProcessingFilter() {
        return tokenResolutionProcessingFilter;
    }

    public void setTokenResolutionProcessingFilter(TokenResolutionProcessingFilter tokenResolutionProcessingFilter) {
        this.tokenResolutionProcessingFilter = tokenResolutionProcessingFilter;
    }
}
