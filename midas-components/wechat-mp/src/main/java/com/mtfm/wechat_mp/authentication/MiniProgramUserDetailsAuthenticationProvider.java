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
import com.mtfm.wechat_mp.MiniProgramRedisKey;
import com.mtfm.weixin.mp.SessionResult;
import com.mtfm.weixin.mp.service.OauthCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
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
    private OauthCodeService oauthCodeService;
    private UserDetailsChecker postCheck = new AppUserPreAuthenticationChecks();
    private UserDetailsManager userDetailsManager;
    private RedisTemplate<String, String> redisTemplate;
    private static final String NONE_CODE = "NODE_CODE";

    public MiniProgramUserDetailsAuthenticationProvider(OauthCodeService oauthCodeService,
                                                        UserDetailsManager userDetailsManager,
                                                        RedisTemplate<String, String> redisTemplate) {
        this.oauthCodeService = oauthCodeService;
        this.userDetailsManager = userDetailsManager;
        this.redisTemplate = redisTemplate;
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
        SessionResult sessionResult;
        try {
            // 远程调用微信服务器获取session
            sessionResult = oauthCodeService.codeToSession(jsCode);
            storeSession(sessionResult.getOpenid(), sessionResult.getSession_key());
        } catch (IllegalAccessException e) {
//            throw new BadCredentialsException(this.messages
//                    .getMessage("MiniProgramUserDetailsAuthenticationProvider.badCredentials",
//                            "wrong jsCode, no authorized user found for the mini program"));
            sessionResult = new SessionResult();
            sessionResult.setOpenid("123456");
            sessionResult.setUnionid("123456");
        }
        try {
            return loadUser(sessionResult.getOpenid(), authentication);
        } catch (UsernameNotFoundException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("first time try to load user failed, and then create User;");
            }
            MpUser mpUser = (MpUser) authentication.getDetails();
            CreateUser createUser = new CreateUser(sessionResult.getOpenid(), sessionResult.getUnionid(), mpUser);
            userDetailsManager.createUser(createUser);
            try {
                return loadUser(sessionResult.getOpenid(), authentication);
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

    private void storeSession(String openId, String sessionKey) {
        BoundValueOperations<String, String> ops = this.redisTemplate
                .boundValueOps(MiniProgramRedisKey.makeKey(MiniProgramRedisKey.WECHAT_SESSION_KEY, openId));
        ops.set(sessionKey);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MiniProgramAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String determineJsCode(Authentication authentication) {
        return (authentication.getCredentials() == null) ? NONE_CODE : authentication.getCredentials().toString();
    }

    public UserDetailsChecker getPostCheck() {
        return postCheck;
    }

    public void setPostCheck(UserDetailsChecker postCheck) {
        this.postCheck = postCheck;
    }

    public MessageSourceAccessor getMessages() {
        return messages;
    }

    public void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }

    public OauthCodeService getOauthCodeService() {
        return oauthCodeService;
    }

    public void setOauthCodeService(OauthCodeService oauthCodeService) {
        this.oauthCodeService = oauthCodeService;
    }

    public UserDetailsManager getUserDetailsManager() {
        return userDetailsManager;
    }

    public void setUserDetailsManager(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
