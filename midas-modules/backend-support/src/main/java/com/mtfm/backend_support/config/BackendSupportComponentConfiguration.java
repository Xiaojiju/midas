package com.mtfm.backend_support.config;

import com.mtfm.backend_support.provisioning.GroupAuthorityManager;
import com.mtfm.backend_support.provisioning.ManageUserDetailsService;
import com.mtfm.backend_support.provisioning.RouterManager;
import com.mtfm.backend_support.provisioning.UserProfileManager;
import com.mtfm.backend_support.provisioning.authority.SimpleUserProfile;
import com.mtfm.backend_support.provisioning.mapper.*;
import com.mtfm.backend_support.provisioning.service.*;
import com.mtfm.security.config.WebAutoSecurityConfiguration;
import com.mtfm.security.provisioning.PermissionGrantedManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

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
    public UserDetailsManager userDetailsManager(UsernameMapper usernameMapper, UserSecretMapper userSecretMapper,
                                                 GroupAuthorityManager groupAuthorityManager, UserManageMapper userManageMapper,
                                                 UserDetailsService userDetailsService) {
        return new UserManageService(usernameMapper, userSecretMapper, groupAuthorityManager, userManageMapper, userDetailsService);
    }

    @Bean
    public UserDetailsService userDetailsService(PermissionGrantedManager permissionGrantedManager, UserManageMapper userManageMapper) {
        return new UserDetailsManageService(permissionGrantedManager, userManageMapper, configuration.isEnablePermissions());
    }

    @Bean
    public GroupAuthorityManager groupAuthorityManager(AuthorityMapper authorityMapper,
                                                       UserManageMapper userManageMapper,
                                                       RouterMapper routerMapper) {
        return new AuthorityGroupManager(authorityMapper, userManageMapper, routerMapper);
    }

    @Bean
    public ManageUserDetailsService<SimpleUserProfile> manageUserDetailsService(UserDetailsManager userDetailsManager,
                                                                                UserProfileMapper userProfileMapper,
                                                                                UsernameMapper usernameMapper,
                                                                                UserManageMapper userManageMapper) {
        return new UserManageServiceTemplate(userDetailsManager, userProfileMapper, usernameMapper, userManageMapper);
    }

    @Bean
    public RouterManager routerManager(RouterMapper routerMapper, UserManageMapper userManageMapper, GroupAuthorityManager groupAuthorityManager) {
        return new RouterManageService(routerMapper, userManageMapper, groupAuthorityManager);
    }

    @Bean
    public UserProfileManager userProfileManager(UserProfileMapper userProfileMapper) {
        return new UserProfileManageService(userProfileMapper);
    }
}
