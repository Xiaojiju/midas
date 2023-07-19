/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.wechat_mp.authentication;


import com.mtfm.security.authentication.AppUserPreAuthenticationChecks;
import com.mtfm.wechat_mp.MiniProgramMessageSource;
import com.mtfm.weixin.mp.PhoneResult;
import com.mtfm.weixin.mp.service.AccessTokenService;
import com.mtfm.weixin.mp.service.OauthCodeService;
import com.mtfm.weixin.mp.service.PhoneInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 小程序认证，需要自定义实现{@link UserDetailsManager}
 */
public class MiniProgramUserDetailsAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(MiniProgramUserDetailsAuthenticationProvider.class);

    private MessageSourceAccessor messages = MiniProgramMessageSource.getAccessor();

    private final OauthCodeService oauthCodeService;

    private PhoneInfoService phoneInfoService;

    private AccessTokenService accessTokenService;

    private UserDetailsChecker postCheck = new AppUserPreAuthenticationChecks();

    private final UserDetailsManager userDetailsManager;

    private static final String NONE_CODE = "NODE_CODE";

    public MiniProgramUserDetailsAuthenticationProvider(OauthCodeService oauthCodeService,
                                                        UserDetailsManager userDetailsManager) {
        this.oauthCodeService = oauthCodeService;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "a message source must be set");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(MiniProgramAuthenticationToken.class, authentication,
                () -> this.messages.getMessage("MiniProgramUserDetailsAuthenticationProvider.onlySupports",
                        "Only MiniProgramAuthenticationToken is supported"));
        String jsCode = determineJsCode(authentication);
        String phoneNumber;
        try {
            PhoneResult phoneResult = phoneInfoService.getPhone(jsCode);
            phoneNumber = phoneResult.getPhone_info().getPhoneNumber();
//            storeSession(sessionResult.getOpenid(), sessionResult.getSession_key());
        } catch (IllegalAccessException e) {
            throw new BadCredentialsException(this.messages
                    .getMessage("MiniProgramUserDetailsAuthenticationProvider.badCredentials",
                            "wrong jsCode, no authorized user found for the mini program"));
        }

        try {
            return loadUser(phoneNumber, authentication);
        } catch (UsernameNotFoundException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("first time try to load user failed, and then create User;");
            }
            MpUser mpUser = (MpUser) authentication.getDetails();
            MpUserDetails registry = new MpUserDetails(phoneNumber, mpUser);
            userDetailsManager.createUser(registry);
            try {
                return loadUser(phoneNumber, authentication);
            } catch (UsernameNotFoundException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("could not found user after retry create user, please check target class which " +
                            "implement UserDetailsManager");
                }
                throw new UsernameNotFoundException(this.messages.getMessage(
                        "MiniProgramUserDetailsAuthenticationProvider.createUser",
                        "could not found user after retry create user, please check target class which " +
                                "implement UserDetailsManager"));
            }
        }
    }

    protected Authentication loadUser(String username, Authentication authentication) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        Assert.notNull(userDetails, "retrieveUser returned null - a violation of the interface contract");
        postCheck.check(userDetails);
        return MiniProgramAuthenticationToken.authenticated(
                userDetails, authentication, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MiniProgramAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String determineJsCode(Authentication authentication) {
        return (authentication.getCredentials() == null) ? NONE_CODE : authentication.getCredentials().toString();
    }

    protected UserDetailsChecker getPostCheck() {
        return postCheck;
    }

    public void setPostCheck(UserDetailsChecker postCheck) {
        this.postCheck = postCheck;
    }

    protected MessageSourceAccessor getMessages() {
        return messages;
    }

    public void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }

    protected OauthCodeService getOauthCodeService() {
        return oauthCodeService;
    }

    protected UserDetailsManager getUserDetailsManager() {
        return userDetailsManager;
    }

}
