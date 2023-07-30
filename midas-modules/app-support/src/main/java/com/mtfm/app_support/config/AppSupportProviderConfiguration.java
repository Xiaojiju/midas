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

import com.mtfm.app_support.mapper.AppUserMapper;
import com.mtfm.app_support.service.AppUserAccountService;
import com.mtfm.app_support.service.AppUserBaseInfoService;
import com.mtfm.app_support.service.AppUserReferenceService;
import com.mtfm.app_support.service.AppUserSecretService;
import com.mtfm.app_support.service.user.*;
import com.mtfm.wechat_mp.authentication.MiniProgramPhoneAuthenticationProvider;
import com.mtfm.weixin.mp.service.PhoneInfoService;
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
     * @param phoneInfoService 远程微信小程序服务
     * @param userDetailsManager 用户加载
     * @return 微信小程序认证处理器
     */
    @Bean
    public MiniProgramPhoneAuthenticationProvider miniProgramUserDetailsAuthenticationProvider(
            PhoneInfoService phoneInfoService, MiniProgramUserProxyAdapter userDetailsManager) {
        return new MiniProgramPhoneAuthenticationProvider(userDetailsManager, phoneInfoService);
    }

    @Bean
    public AppUserSecretService appUserSecretService() {
        return new AppUserSecretServiceImpl();
    }

    @Bean
    public AppUserAccountService appUserAccountService() {
        return new AppUserAccountServiceImpl();
    }

    @Bean
    public AppUserBaseInfoService appUserBaseInfoService() {
        return new AppUserBaseInfoServiceImpl();
    }

    @Bean
    public AppUserReferenceService appUserReferenceService() {
        return new AppUserReferenceServiceImpl();
    }

    @Bean
    public AppUserAccountManageService appUserManageService(AppUserMapper appUserMapper,
                                                            AppUserReferenceService appUserReferenceService,
                                                            AppUserSecretService appUserSecretService) {
        return new AppUserAccountManageService(appUserMapper, appUserReferenceService, appUserSecretService);
    }

    @Bean
    public MiniProgramUserProxyAdapter miniProgramUserProxyAdapter(AppUserAccountManageService appUserAccountManageService,
                                                                   AppUserBaseInfoService appUserBaseInfoService) {
        return new MiniProgramUserProxyAdapter(appUserAccountManageService, appUserBaseInfoService);
    }

}
