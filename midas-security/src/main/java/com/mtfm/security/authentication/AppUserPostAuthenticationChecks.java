package com.mtfm.security.authentication;

import com.mtfm.security.context.SolarMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;

public class AppUserPostAuthenticationChecks implements UserDetailsChecker, MessageSourceAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AppUserPostAuthenticationChecks.class);
    private final boolean enableCredentialExpired;
    private MessageSourceAccessor messages = SolarMessageSource.getAccessor();

    public AppUserPostAuthenticationChecks() {
        this(false);
    }

    public AppUserPostAuthenticationChecks(boolean enableCredentialExpired) {
        this.enableCredentialExpired = enableCredentialExpired;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source could not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void check(UserDetails user) {
        if (!this.enableCredentialExpired) {
            return ;
        }
        if (!user.isCredentialsNonExpired()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Failed to authenticate since user account credentials have expired");
            }
            throw new CredentialsExpiredException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired",
                            "User credentials have expired"));
        }
    }
}
