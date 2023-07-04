package com.mtfm.backend_support.config;

import com.mtfm.backend_support.service.*;
import com.mtfm.backend_support.service.mapper.BackendUserServiceMapper;
import com.mtfm.backend_support.service.mapper.UserRoleMapper;
import com.mtfm.backend_support.service.secret.UserSecretManagerService;
import com.mtfm.backend_support.service.user.*;
import com.mtfm.security.config.WebAutoSecurityConfiguration;
import com.mtfm.security.service.GrantAuthorityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BackendSupportComponentConfiguration {

    private final WebAutoSecurityConfiguration configuration;

    public BackendSupportComponentConfiguration(WebAutoSecurityConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * {@link UserDetailsService}为通过用户名进行加载用户，其方法{@link UserDetailsService#loadUserByUsername(String)}，可以直接
     * 重写该方法，进行自定义装配
     * @return 用户业务处理类 {@link UserDetailsService}
     */
    @Bean
    public UserDetailsManageService userDetailsManageService(BackendUserServiceMapper backendUserServiceMapper,
                                                             GrantAuthorityService grantAuthorityService) {
        UserDetailsManageService userDetailsManageService
                = new UserDetailsManageService(backendUserServiceMapper, this.configuration.isEnablePermissions());
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
    public UserManageService userManageService(UserDetailsManageService userDetailsManageService,
                                               UserRoleManager userRoleManager) {
        return new UserManageService(userDetailsManageService, userRoleManager);
    }

    @Bean
    public UserInformationManageService userInformationManageService(UserManageService userManageService) {
        return new UserInformationManageService(userManageService);
    }

    @Bean
    public GrantAuthorityService grantAuthorityService(UserRoleManager userRoleManager) {
        return new GrantUserAuthorityService(userRoleManager);
    }
}
