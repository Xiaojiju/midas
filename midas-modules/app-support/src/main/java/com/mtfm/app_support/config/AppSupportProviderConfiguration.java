package com.mtfm.app_support.config;

import com.mtfm.app_support.service.user.AppUserManageService;
import com.mtfm.app_support.service.user.MiniProgramUserProxyAdapter;
import com.mtfm.security.cache.RedisUserCache;
import com.mtfm.security.core.*;
import com.mtfm.security.filter.ReturnResponseAuthenticationFailHandler;
import com.mtfm.security.filter.ReturnResponseAuthenticationSuccessHandler;
import com.mtfm.wechat_mp.authentication.MiniProgramUserDetailsAuthenticationProvider;
import com.mtfm.wechat_mp.filter.MiniProgramAuthenticationProcessingFilter;
import com.mtfm.weixin.mp.service.OauthCodeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class AppSupportProviderConfiguration {

    @Bean
    public MiniProgramAuthenticationProcessingFilter miniProgramAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        MiniProgramAuthenticationProcessingFilter filter = new MiniProgramAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public MiniProgramUserDetailsAuthenticationProvider miniProgramUserDetailsAuthenticationProvider(
            OauthCodeService oauthCodeService, MiniProgramUserProxyAdapter userDetailsManager, RedisTemplate<String, String> redisTemplate) {
        return new MiniProgramUserDetailsAuthenticationProvider(oauthCodeService, userDetailsManager, redisTemplate);
    }

}
