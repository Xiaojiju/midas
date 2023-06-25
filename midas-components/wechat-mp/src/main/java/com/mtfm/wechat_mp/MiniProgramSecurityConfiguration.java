package com.mtfm.wechat_mp;

import com.mtfm.security.core.LocalSession;
import com.mtfm.security.core.SessionContext;
import com.mtfm.wechat_mp.filter.MiniProgramAuthenticationProcessingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;

public class MiniProgramSecurityConfiguration {

    @Bean
    public MiniProgramAuthenticationProcessingFilter miniProgramAuthenticationProcessingFilter(
            AuthenticationManager authenticationManager,
            SessionContext<LocalSession> subjectSessionContext) {
        MiniProgramAuthenticationProcessingFilter filter = new MiniProgramAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
}


