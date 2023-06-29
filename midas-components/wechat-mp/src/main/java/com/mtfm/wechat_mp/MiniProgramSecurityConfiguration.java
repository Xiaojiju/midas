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
package com.mtfm.wechat_mp;

import com.mtfm.security.core.LocalSession;
import com.mtfm.security.core.LocalSessionProvider;
import com.mtfm.security.core.SessionContext;
import com.mtfm.security.filter.ReturnResponseAuthenticationFailHandler;
import com.mtfm.security.filter.ReturnResponseAuthenticationSuccessHandler;
import com.mtfm.wechat_mp.authentication.MiniProgramUserDetailsAuthenticationProvider;
import com.mtfm.wechat_mp.filter.MiniProgramAuthenticationProcessingFilter;
import com.mtfm.weixin.mp.service.OauthCodeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 小程序配置
 */
public class MiniProgramSecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean(MiniProgramAuthenticationProcessingFilter.class)
    public MiniProgramAuthenticationProcessingFilter miniProgramAuthenticationProcessingFilter(
            AuthenticationManager authenticationManager, LocalSessionProvider localSessionProvider, SessionContext<LocalSession> subjectSessionContext) {
        AuthenticationSuccessHandler successHandler = new ReturnResponseAuthenticationSuccessHandler(subjectSessionContext, localSessionProvider);
        MiniProgramAuthenticationProcessingFilter filter = new MiniProgramAuthenticationProcessingFilter(successHandler, new ReturnResponseAuthenticationFailHandler());
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    @ConditionalOnMissingBean(MiniProgramUserDetailsAuthenticationProvider.class)
    public MiniProgramUserDetailsAuthenticationProvider miniProgramUserDetailsAuthenticationProvider(
            OauthCodeService oauthCodeService, UserDetailsManager userDetailsManager, RedisTemplate<String, String> redisTemplate) {
        return new MiniProgramUserDetailsAuthenticationProvider(oauthCodeService, userDetailsManager, redisTemplate);
    }

}


