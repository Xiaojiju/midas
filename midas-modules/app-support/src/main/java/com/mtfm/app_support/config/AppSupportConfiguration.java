package com.mtfm.app_support.config;

import com.mtfm.security.filter.RequestBodyAuthenticationProcessingFilter;
import com.mtfm.security.filter.RequestBodyLogoutFilter;
import com.mtfm.security.filter.TokenResolutionProcessingFilter;
import com.mtfm.wechat_mp.filter.MiniProgramAuthenticationProcessingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
public class AppSupportConfiguration {

    private final MiniProgramAuthenticationProcessingFilter filter;

    public AppSupportConfiguration(MiniProgramAuthenticationProcessingFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.logout().disable()
                .csrf().disable()
                .addFilterBefore(new RequestBodyLogoutFilter(), LogoutFilter.class)
                .addFilterBefore(this.filter, RequestBodyLogoutFilter.class)
                .addFilterBefore(new TokenResolutionProcessingFilter("/solar/api/v1/login/mp"),
                        MiniProgramAuthenticationProcessingFilter.class)
                .sessionManagement().disable();
        return security.build();
    }
    
}
