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

import com.mtfm.app_support.service.user.MiniProgramUserProxyAdapter;
import com.mtfm.wechat_mp.authentication.MiniProgramUserDetailsAuthenticationProvider;
import com.mtfm.weixin.mp.service.OauthCodeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * provider配置类
 * 直接将{@link org.springframework.security.authentication.AuthenticationProvider}的实现类注入到容器中，
 * 在{@link com.mtfm.security.config.WebAutoSecurityConfiguration#authenticationManager(List)}会自动注入到管理器中
 */
@Configuration
public class AppSupportProviderConfiguration {
    /**
     * 注入小程序认证类
     * @param oauthCodeService 远程微信小程序服务
     * @param userDetailsManager 用户加载
     * @return 微信小程序认证处理器
     */
    @Bean
    public MiniProgramUserDetailsAuthenticationProvider miniProgramUserDetailsAuthenticationProvider(
            OauthCodeService oauthCodeService, MiniProgramUserProxyAdapter userDetailsManager) {
        return new MiniProgramUserDetailsAuthenticationProvider(oauthCodeService, userDetailsManager);
    }

}
