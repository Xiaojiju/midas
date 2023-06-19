package com.mtfm.backend_support.config;

import com.mtfm.backend_support.service.*;
import com.mtfm.backend_support.service.secret.UserSecretManagerService;
import com.mtfm.backend_support.service.user.*;
import com.mtfm.backend_support.service.mapper.*;
import com.mtfm.security.config.WebSecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.userdetails.UserDetailsService;

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
    public UserDetailsService userDetailsService(BackendUserServiceMapper backendUserServiceMapper) {
        return new UserDetailsManageService(backendUserServiceMapper, webSecurityProperties.isEnablePermissions());
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

    public WebSecurityProperties getWebSecurityProperties() {
        return webSecurityProperties;
    }

    public void setWebSecurityProperties(WebSecurityProperties webSecurityProperties) {
        this.webSecurityProperties = webSecurityProperties;
    }

}
