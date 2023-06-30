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

import com.mtfm.security.authentication.UserTemplatePreAuthenticationChecks;
import com.mtfm.security.cache.RedisUserCache;
import com.mtfm.security.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 */
@Configuration
public class WebAuthenticationConfig {

    private final RedisTemplate<String, String> redisTemplate;
    private final WebSecurityProperties webSecurityProperties;
    private final WebAuthProperties webAuthProperties;

    public WebAuthenticationConfig(RedisTemplate<String, String> redisTemplate,
                                   WebSecurityProperties webSecurityProperties,
                                   WebAuthProperties webAuthProperties) {
        this.redisTemplate = redisTemplate;
        this.webSecurityProperties = webSecurityProperties;
        this.webAuthProperties = webAuthProperties;
    }

    @Bean
    @ConditionalOnMissingBean(RedisUserCache.class)
    public RedisUserCache redisUserCache() {
        return new RedisUserCache(redisTemplate);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsChecker userDetailsChecker(WebSecurityProperties webSecurityProperties) {
        return new UserTemplatePreAuthenticationChecks(webSecurityProperties);
    }

    /**
     * 数据库读取密钥认证提供器
     * 如果需要修改密钥的加密方式，则需要在{@link DaoAuthenticationProvider}重新
     * 注入{@link org.springframework.security.crypto.password.PasswordEncoder},如果需要自定义加密方式，有两种方式，第一种是
     * 在spring容器中注入自定义的PasswordEncoder实现类，第二种是自定义创建{@link PasswordEncoderFactories}，更优雅的方式是两种结合
     * 起来使用。
     * 如果需要更多的自定义验证密码的方式，则需要重写{@link DaoAuthenticationProvider}中的additionalAuthenticationChecks方法。
     */
    @Bean
    @ConditionalOnMissingBean(DaoAuthenticationProvider.class)
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
                                                               MessageSource messageSource,
                                                               RedisUserCache redisUserCache,
                                                               UserDetailsChecker userDetailsChecker) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setMessageSource(messageSource);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setForcePrincipalAsString(false);
        daoAuthenticationProvider.setUserCache(redisUserCache);
        daoAuthenticationProvider.setPreAuthenticationChecks(userDetailsChecker);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public AnySessionContext<UserSubject> anySessionContext() {
        return new AnySessionContextHandler(redisTemplate);
    }

    @Bean
    public SessionContext<LocalSession> subjectSessionContext(AnySessionContext<UserSubject> anySessionContext,
                                                             LocalSessionProvider localSessionProvider,
                                                             WebSecurityProperties webSecurityProperties) {
        return new SecuritySessionContextHolder(anySessionContext, localSessionProvider, webSecurityProperties);
    }

    @Bean
    public LocalSessionProvider localSessionProvider() {
        return new LocalSessionProvider(webAuthProperties);
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public WebSecurityProperties getWebSecurityProperties() {
        return webSecurityProperties;
    }
}
