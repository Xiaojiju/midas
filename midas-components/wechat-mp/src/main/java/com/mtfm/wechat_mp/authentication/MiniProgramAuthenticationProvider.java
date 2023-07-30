package com.mtfm.wechat_mp.authentication;

import com.mtfm.security.authentication.AppUserPreAuthenticationChecks;
import com.mtfm.wechat_mp.MiniProgramMessageSource;
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

public abstract class MiniProgramAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(MiniProgramAuthenticationProvider.class);

    private MessageSourceAccessor messages = MiniProgramMessageSource.getAccessor();

    private UserDetailsChecker postCheck = new AppUserPreAuthenticationChecks();

    private final UserDetailsManager userDetailsManager;

    protected static final String NONE_CODE = "NODE_CODE";

    public MiniProgramAuthenticationProvider(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public MiniProgramAuthenticationProvider(UserDetailsChecker postCheck, UserDetailsManager userDetailsManager) {
        this.postCheck = postCheck;
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
        String username;
        try {
            username = tryUser(authentication);
        } catch (IllegalAccessException e) {
            throw new BadCredentialsException(this.messages
                    .getMessage("MiniProgramUserDetailsAuthenticationProvider.badCredentials",
                            "wrong jsCode, no authorized user found for the mini program"));
        }

        try {
            return loadUser(username, authentication);
        } catch (UsernameNotFoundException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("first time try to load user failed, and then create User;");
            }
            MpUser mpUser = (MpUser) authentication.getDetails();
            MpUserDetails registry = new MpUserDetails(username, mpUser);
            userDetailsManager.createUser(registry);
            try {
                return loadUser(username, authentication);
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

    protected abstract String tryUser(Authentication authentication) throws IllegalAccessException;

    protected Authentication loadUser(String username, Authentication authentication) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        Assert.notNull(userDetails, "retrieveUser returned null - a violation of the interface contract");
        postCheck.check(userDetails);
        return MiniProgramAuthenticationToken.authenticated(
                userDetails, authentication, null, userDetails.getAuthorities());
    }

    private String determineJsCode(Authentication authentication) {
        return (authentication.getCredentials() == null) ? NONE_CODE : authentication.getCredentials().toString();
    }

    protected UserDetailsChecker getPostCheck() {
        return postCheck;
    }

    protected MessageSourceAccessor getMessages() {
        return messages;
    }

    protected UserDetailsManager getUserDetailsManager() {
        return userDetailsManager;
    }
}
