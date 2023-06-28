package com.mtfm.app_support.service.user;

import com.mtfm.app_support.mapper.AppUserMapper;
import com.mtfm.app_support.service.provisioning.AppUser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class AppUserDetailsService implements UserDetailsService, MessageSourceAware, InitializingBean {

    private AppUserMapper appUserMapper;
    private MessageSourceAccessor messages;

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
        return appUser;
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
}
