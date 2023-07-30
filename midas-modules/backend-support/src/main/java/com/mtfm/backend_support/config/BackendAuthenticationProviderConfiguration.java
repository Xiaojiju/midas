package com.mtfm.backend_support.config;

import com.mtfm.security.AppUser;
import com.mtfm.security.authentication.AppUserPostAuthenticationChecks;
import com.mtfm.security.authentication.AppUserPreAuthenticationChecks;
import com.mtfm.security.config.WebAutoSecurityConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BackendAuthenticationProviderConfiguration {

    private final WebAutoSecurityConfiguration configuration;

    public BackendAuthenticationProviderConfiguration(WebAutoSecurityConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * 保留该认证器，其中{@link AppUser#getSecretAccess()}为保留字段，添加第三方认证则使用该字段，需要重写additionalAuthenticationChecks
     * 来跳过密钥验证
     * @param userDetailsManageService 用户详情业务处理类
     * @return 数据库查询认证器
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPreAuthenticationChecks(new AppUserPreAuthenticationChecks());
        provider.setPostAuthenticationChecks(new AppUserPostAuthenticationChecks(configuration.isEnableCredentialsExpired()));
        return provider;
    }
}
