package com.mtfm.app.config;

import com.mtfm.wechat_mp.filter.MiniProgramAuthenticationProcessingFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@MapperScan("com.mtfm.app_support.mapper")
public class MidasAppSecurityConfiguration {

    private MiniProgramAuthenticationProcessingFilter miniProgramAuthenticationProcessingFilter;

    public MidasAppSecurityConfiguration(MiniProgramAuthenticationProcessingFilter miniProgramAuthenticationProcessingFilter) {
        this.miniProgramAuthenticationProcessingFilter = miniProgramAuthenticationProcessingFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.logout().disable()
                .csrf().disable()
                .addFilterAfter(miniProgramAuthenticationProcessingFilter, LogoutFilter.class)
                .sessionManagement().disable();
        return security.build();
    }

    public MiniProgramAuthenticationProcessingFilter getMiniProgramAuthenticationProcessingFilter() {
        return miniProgramAuthenticationProcessingFilter;
    }

    public void setMiniProgramAuthenticationProcessingFilter(MiniProgramAuthenticationProcessingFilter miniProgramAuthenticationProcessingFilter) {
        this.miniProgramAuthenticationProcessingFilter = miniProgramAuthenticationProcessingFilter;
    }
}
