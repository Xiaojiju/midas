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

import com.mtfm.backend_support.service.*;
import com.mtfm.backend_support.service.mapper.BackendUserServiceMapper;
import com.mtfm.backend_support.service.mapper.UserRoleMapper;
import com.mtfm.backend_support.service.secret.UserSecretManagerService;
import com.mtfm.backend_support.service.user.*;
import com.mtfm.security.config.WebSecurityProperties;
import com.mtfm.security.service.GrantAuthorityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * backend-support模块中所有的业务管理器，该类主要是为了将雨雾类注入进Spring的容器中
 */
@Configuration
@EnableAsync
public class ServiceComponents {

    private WebSecurityProperties webSecurityProperties;

    public ServiceComponents(WebSecurityProperties webSecurityProperties,
                             ResourceBundleMessageSource messageSource) {
        this.webSecurityProperties = webSecurityProperties;
        messageSource.addBasenames("i18n/backend_support_messages");
    }

    /**
     * {@link UserDetailsService}为通过用户名进行加载用户，其方法{@link UserDetailsService#loadUserByUsername(String)}，可以直接
     * 重写该方法，进行自定义装配
     * @return 用户业务处理类 {@link UserDetailsService}
     */
    @Bean
    public UserDetailsService userDetailsService(BackendUserServiceMapper backendUserServiceMapper,
                                                 GrantAuthorityService grantAuthorityService) {
        UserDetailsManageService userDetailsManageService
                = new UserDetailsManageService(backendUserServiceMapper, webSecurityProperties.isEnablePermissions());
        userDetailsManageService.setGrantAuthorityService(grantAuthorityService);
        return userDetailsManageService;
    }

    @Bean
    public RoleManager roleService() {
        return new RoleDetailsManageService();
    }

    @Bean
    public UserRoleManager userRoleManager(UserRoleMapper userRoleMapper) {
        return new UserRoleManageService(userRoleMapper);
    }

    @Bean
    public RouterManager routerManager(UserRoleManager userRoleManager) {
        return new GrantUserAuthorityService(userRoleManager);
    }

    @Bean
    public UserReferenceManager userReferenceManager() {
        return new UserReferenceManagerService();
    }

    @Bean
    public UserSecretManager userSecretManager() {
        return new UserSecretManagerService();
    }

    @Bean
    public UserBaseInfoManager userBaseInfoManager() {
        return new UserBaseInfoManagerService();
    }

    @Bean
    public UserManageService userManageService(UserDetailsService userDetailsService,
                                                 UserReferenceManager userReferenceManager,
                                                 UserSecretManager userSecretManager,
                                                 UserRoleManager userRoleManager) {
        return new UserManageService(userDetailsService,
                userReferenceManager, userSecretManager, userRoleManager);
    }

    @Bean
    public UserInformationManageService userInformationManageService(UserManageService userManageService,
                                                                     UserBaseInfoManager userBaseInfoManager) {
        return new UserInformationManageService(userManageService, userBaseInfoManager);
    }

    @Bean
    public GrantAuthorityService grantAuthorityService(UserRoleManager userRoleManager) {
        return new GrantUserAuthorityService(userRoleManager);
    }

    public WebSecurityProperties getWebSecurityProperties() {
        return webSecurityProperties;
    }

    public void setWebSecurityProperties(WebSecurityProperties webSecurityProperties) {
        this.webSecurityProperties = webSecurityProperties;
    }

}
