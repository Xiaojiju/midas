package com.mtfm.app_support.config;

import com.mtfm.app_support.service.user.AppUserManageService;
import com.mtfm.app_support.service.user.MiniProgramUserProxyAdapter;
import com.mtfm.security.cache.RedisUserCache;
import com.mtfm.security.core.LocalSession;
import com.mtfm.security.core.SessionContext;
import com.mtfm.security.filter.TokenResolutionProcessingFilter;
import com.mtfm.wechat_mp.authentication.MiniProgramUserDetailsAuthenticationProvider;
import com.mtfm.weixin.mp.service.OauthCodeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppSupportProviderConfiguration {

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(AppUserManageService appUserManageService,
                                                               MessageSource messageSource,
                                                               RedisUserCache redisUserCache,
                                                               UserDetailsChecker userDetailsChecker) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setMessageSource(messageSource);
        daoAuthenticationProvider.setUserDetailsService(appUserManageService);
        daoAuthenticationProvider.setForcePrincipalAsString(false);
        daoAuthenticationProvider.setUserCache(redisUserCache);
        daoAuthenticationProvider.setPreAuthenticationChecks(userDetailsChecker);
        return daoAuthenticationProvider;
    }

    @Bean
    public MiniProgramUserDetailsAuthenticationProvider miniProgramUserDetailsAuthenticationProvider(
            OauthCodeService oauthCodeService, MiniProgramUserProxyAdapter userDetailsManager, RedisTemplate<String, String> redisTemplate) {
        return new MiniProgramUserDetailsAuthenticationProvider(oauthCodeService, userDetailsManager, redisTemplate);
    }

//    @Bean
//    public TokenResolutionProcessingFilter tokenResolutionProcessingFilter(SessionContext<LocalSession> sessionContext) {
//        List<String> skipUrls = new ArrayList<>();
//        skipUrls.add("/solar/api/v1/mp/login");
//        return new TokenResolutionProcessingFilter(skipUrls.toArray(new String[0]), sessionContext);
//    }
}
