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
package com.mtfm.backend_support.config;

import com.mtfm.security.config.WebAutoSecurityConfiguration;
import com.mtfm.security.core.HttpRequestSessionHandler;
import com.mtfm.security.core.SecuritySessionContextHolder;
import com.mtfm.security.filter.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * SpringSecurity Filter 组装类
 * 由于使用Filter是继承{@link org.springframework.web.filter.GenericFilterBean},在将Filter注入到容器后，也会进入Spring MVC中
 * {@link org.springframework.web.filter.DelegatingFilterProxy}对象中，由于该对象直接交流Servlet管理（xml配置直接配置在web.xml
 * 上），执行完后，才会直接SpringSecurity的过滤链，所以就会执行两次Filter，导致在一些场景中，比如获取Request的InputStream，就是出现Stream
 * close 的问题；
 * 解决这个问题有两种方法：
 *      1. 直接将filter示例注入到HttpSecurity中，不进行容器管理；
 *      2. 另一种我还在探索
 */
@Configuration
public class ImportBackendFilter {

    private final SecuritySessionContextHolder securitySessionContextHolder;
    private final AuthenticationManager authenticationManager;
    private final ReturnResponseAuthenticationSuccessHandler successHandler;
    private final ReturnResponseAuthenticationFailHandler failHandler;
    private final WebAutoSecurityConfiguration configuration;

    public ImportBackendFilter(SecuritySessionContextHolder securitySessionContextHolder,
                               AuthenticationManager authenticationManager,
                               ReturnResponseAuthenticationSuccessHandler successHandler,
                               ReturnResponseAuthenticationFailHandler failHandler,
                               WebAutoSecurityConfiguration configuration) {
        this.securitySessionContextHolder = securitySessionContextHolder;
        this.authenticationManager = authenticationManager;
        this.successHandler = successHandler;
        this.failHandler = failHandler;
        this.configuration = configuration;
    }

    public RequestBodyLogoutFilter requestBodyLogoutFilter() {
        return new RequestBodyLogoutFilter(new JsonBasedLogoutSuccessHandler(), new CacheClearLogoutHandler(securitySessionContextHolder));
    }

    public TokenResolutionProcessingFilter tokenResolutionProcessingFilter() {
        TokenResolutionProcessingFilter filter =
                new TokenResolutionProcessingFilter(securitySessionContextHolder, "/solar/api/v1/login/mp");
        filter.setHttpRequestSessionHandler(new HttpRequestSessionHandler(configuration.getHeader()));
        return filter;
    }

    public RequestBodyAuthenticationProcessingFilter requestBodyAuthenticationProcessingFilter() {
        RequestBodyAuthenticationProcessingFilter filter = new RequestBodyAuthenticationProcessingFilter();
        filter.setAuthenticationSuccessHandler(this.successHandler);
        filter.setAuthenticationFailureHandler(this.failHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }
}
