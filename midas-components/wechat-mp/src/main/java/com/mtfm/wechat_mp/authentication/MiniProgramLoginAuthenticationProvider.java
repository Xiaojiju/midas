package com.mtfm.wechat_mp.authentication;

import com.mtfm.weixin.mp.SessionResult;
import com.mtfm.weixin.mp.service.OauthCodeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.provisioning.UserDetailsManager;

public class MiniProgramLoginAuthenticationProvider extends MiniProgramAuthenticationProvider {

    private final OauthCodeService oauthCodeService;

    public MiniProgramLoginAuthenticationProvider(UserDetailsManager userDetailsManager,
                                                  OauthCodeService oauthCodeService) {
        super(userDetailsManager);
        this.oauthCodeService = oauthCodeService;
    }

    public MiniProgramLoginAuthenticationProvider(UserDetailsChecker postCheck,
                                                  UserDetailsManager userDetailsManager,
                                                  OauthCodeService oauthCodeService) {
        super(postCheck, userDetailsManager);
        this.oauthCodeService = oauthCodeService;
    }

    @Override
    protected String tryUser(Authentication authentication) throws IllegalAccessException {
        SessionResult sessionResult = oauthCodeService.codeToSession(determineJsCode(authentication));
        return sessionResult.getOpenid();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MiniProgramAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String determineJsCode(Authentication authentication) {
        return (authentication.getCredentials() == null) ? NONE_CODE : authentication.getCredentials().toString();
    }
}
