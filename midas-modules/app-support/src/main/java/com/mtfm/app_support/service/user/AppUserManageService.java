package com.mtfm.app_support.service.user;

import com.mtfm.app_support.entity.AppAccount;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.mapper.AppUserMapper;
import com.mtfm.app_support.service.AppUserReferenceService;
import com.mtfm.app_support.service.provisioning.AppUser;
import com.mtfm.app_support.service.user.AppUserDetailsService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.SecurityCode;
import com.mtfm.tools.enums.Judge;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Transactional(rollbackFor = Exception.class)
public class AppUserManageService extends AppUserDetailsService implements UserDetailsManager, InitializingBean, MessageSourceAware {

    private MessageSourceAccessor messages;
    private AppUserReferenceService appUserReferenceService;

    public AppUserManageService(AppUserMapper appUserMapper) {
        super(appUserMapper);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(messages, "A message source could not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(AppUser.class, user, "only supports AppUser.class");
        AppUser appUser = (AppUser) user;
        AppAccount appAccount = AppAccount.uncreated(Judge.NO, null);
        super.getAppUserMapper().insert(appAccount);
        String userId = appAccount.getId();
        AppUserReference userReference = AppUserReference.builder(userId).withUsername(appUser.getUsername(), appUser.getIdentifier())
                .enableLogin(true).enableUsingSecretAuthenticated(appUser.getSecretAccess() == Judge.YES)
                .isThirdPart(appUser.getThirdPart() == Judge.YES).validate(appUser.getValidated() == Judge.YES)
                .expiredAt(appUser.getUsernameExpiredTime())
                .withAdditionalKey(appUser.getAdditionalKey()).build();
        appUserReferenceService.save(userReference);

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return super.loadUserByUsername(username);
    }
}
