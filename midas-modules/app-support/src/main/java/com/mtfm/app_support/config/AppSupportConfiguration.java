package com.mtfm.app_support.config;

import com.mtfm.security.filter.RequestBodyAuthenticationProcessingFilter;
import com.mtfm.security.filter.RequestBodyLogoutFilter;
import com.mtfm.security.filter.TokenResolutionProcessingFilter;
import com.mtfm.wechat_mp.filter.MiniProgramAuthenticationProcessingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

public class AppSupportConfiguration {

    private final MiniProgramAuthenticationProcessingFilter miniProgramAuthenticationProcessingFilter;
    private final RequestBodyLogoutFilter requestBodyLogoutFilter;
    private final RequestBodyAuthenticationProcessingFilter requestBodyAuthenticationProcessingFilter;
    private final TokenResolutionProcessingFilter tokenResolutionProcessingFilter;

    public AppSupportConfiguration(MiniProgramAuthenticationProcessingFilter miniProgramAuthenticationProcessingFilter,
                                   RequestBodyLogoutFilter requestBodyLogoutFilter,
                                   RequestBodyAuthenticationProcessingFilter requestBodyAuthenticationProcessingFilter,
                                   TokenResolutionProcessingFilter tokenResolutionProcessingFilter) {
        this.miniProgramAuthenticationProcessingFilter = miniProgramAuthenticationProcessingFilter;
        this.requestBodyLogoutFilter = requestBodyLogoutFilter;
        this.requestBodyAuthenticationProcessingFilter = requestBodyAuthenticationProcessingFilter;
        this.tokenResolutionProcessingFilter = tokenResolutionProcessingFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.logout().disable()
                .csrf().disable()
                .addFilterAfter(miniProgramAuthenticationProcessingFilter, LogoutFilter.class)
                .addFilterAfter(requestBodyAuthenticationProcessingFilter, LogoutFilter.class)
                .addFilterAfter(requestBodyLogoutFilter, LogoutFilter.class)
                .addFilterBefore(tokenResolutionProcessingFilter, RequestBodyAuthenticationProcessingFilter.class)
                .sessionManagement().disable();
        return security.build();
    }
}
