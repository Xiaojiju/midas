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

import com.mtfm.security.core.LocalSession;
import com.mtfm.security.core.LocalSessionProvider;
import com.mtfm.security.core.SessionContext;
import com.mtfm.security.filter.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 */
@Configuration
public class WebSecurityConfig {

    private AuthenticationManager authenticationManager;
    private LocalSessionProvider localSessionProvider;

    public WebSecurityConfig(AuthenticationManager authenticationManager,
                             LocalSessionProvider localSessionProvider) {
        this.authenticationManager = authenticationManager;
        this.localSessionProvider = localSessionProvider;
    }

    @Bean
    @ConditionalOnMissingBean(RequestBodyAuthenticationProcessingFilter.class)
    public RequestBodyAuthenticationProcessingFilter requestBodyAuthenticationFilter(AuthenticationManager authenticationManager,
                                                                                     SessionContext<LocalSession> subjectSessionContext) {
        RequestBodyAuthenticationProcessingFilter requestBodyAuthenticationProcessingFilter = new RequestBodyAuthenticationProcessingFilter();
        requestBodyAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        requestBodyAuthenticationProcessingFilter.setAuthenticationSuccessHandler(
                new ReturnResponseAuthenticationSuccessHandler(subjectSessionContext, localSessionProvider));
        requestBodyAuthenticationProcessingFilter.setAuthenticationFailureHandler(new ReturnResponseAuthenticationFailHandler());
        return requestBodyAuthenticationProcessingFilter;
    }

    @Bean
    public RequestBodyLogoutFilter requestBodyLogoutFilter(SessionContext<LocalSession> sessionContext) {
        return new RequestBodyLogoutFilter(new JsonBasedLogoutSuccessHandler(), new CacheClearLogoutHandler(sessionContext));
    }

    @Bean
    @ConditionalOnMissingBean(TokenResolutionProcessingFilter.class)
    public TokenResolutionProcessingFilter tokenResolutionProcessingFilter(SessionContext<LocalSession> sessionContext) {
        List<String> skipUrls = new ArrayList<>();
        skipUrls.add("/solar/api/v1/login/**");
        return new TokenResolutionProcessingFilter(skipUrls.toArray(new String[0]), sessionContext);
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public LocalSessionProvider getLocalSessionProvider() {
        return localSessionProvider;
    }

    public void setLocalSessionProvider(LocalSessionProvider localSessionProvider) {
        this.localSessionProvider = localSessionProvider;
    }
}
