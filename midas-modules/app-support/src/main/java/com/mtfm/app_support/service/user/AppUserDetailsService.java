package com.mtfm.app_support.service.user;

import com.mtfm.app_support.mapper.AppUserMapper;
import com.mtfm.app_support.service.provisioning.AppUser;
import com.mtfm.security.UserTemplate;
import com.mtfm.security.exceptions.NoAuthoritiesException;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppUserDetailsService implements UserDetailsService, MessageSourceAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);
    private AppUserMapper appUserMapper;
    private MessageSourceAccessor messages;
    private boolean enablePermissions = false;
    private static final String NONE_PASSWORD = "NONE";

    public AppUserDetailsService(AppUserMapper appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.appUserMapper.selectUserByReferenceKey(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(this.messages.getMessage("AppUserDetailsService.userNotFound",
                    new Object[] { username },
                    "could not found app user by username{0}"));
        }
        appUser.setPassword(NONE_PASSWORD);
        return createUserTemplate(username, appUser, new ArrayList<>());
    }

    public UserTemplate createUserTemplate(String username, AppUser appUser, List<GrantedAuthority> permissions) {
        if (appUser == null) {
            logger.debug("username {} not found", username);
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound",
                    new Object[] { username }, "Username {0} wrong with username or password"));
        }
        if (this.enablePermissions) {
            if (CollectionUtils.isEmpty(permissions)) {
                logger.debug("Username {} authorities could not be null", username);
                throw new NoAuthoritiesException(this.messages.getMessage("JdbcDaoImpl.nonPermissions",
                        new Object[] { username }, "Username {0} has no permission"));
            }
        }
        LocalDateTime now = LocalDateTime.now();
        boolean credentialsExpired = false, methodExpired = false, accountExpired = false;
        LocalDateTime credentialsExpiredTime = appUser.getSecretExpiredTime();
        if (credentialsExpiredTime != null) {
            credentialsExpired = credentialsExpiredTime.isBefore(now);
        }
        LocalDateTime methodExpiredTime = appUser.getUsernameExpiredTime();
        if (methodExpiredTime != null) {
            methodExpired = methodExpiredTime.isBefore(now);
        }
        LocalDateTime accountExpiredTime = appUser.getAccountExpiredTime();
        if (accountExpiredTime != null) {
            accountExpired = accountExpiredTime.isBefore(now);
        }
        return new UserTemplate(appUser.getId(), appUser.getPassword(), appUser.getAccountLocked() != Judge.YES,
                !accountExpired, !credentialsExpired, appUser.getAccountLocked() != Judge.YES,
                permissions, appUser.getIdentifier(), !methodExpired,
                appUser.getLoginAccess() != Judge.YES);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(messages, "A message source could not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public AppUserMapper getAppUserMapper() {
        return appUserMapper;
    }

    public void setAppUserMapper(AppUserMapper appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    protected MessageSourceAccessor getMessages() {
        return messages;
    }

    protected void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }

    protected boolean isEnablePermissions() {
        return enablePermissions;
    }

    protected void setEnablePermissions(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }
}
