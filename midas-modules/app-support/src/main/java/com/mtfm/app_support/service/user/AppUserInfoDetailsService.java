package com.mtfm.app_support.service.user;

import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.service.AppUserBaseInfoService;
import com.mtfm.app_support.service.provisioning.AppUserDetails;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

public class AppUserInfoDetailsService implements UserDetailsManager, MessageSourceAware {

    private AppUserManageService appUserManageService;
    private AppUserBaseInfoService appUserBaseInfoService;
    private MessageSourceAccessor messages;

    public AppUserInfoDetailsService(AppUserManageService appUserManageService, AppUserBaseInfoService appUserBaseInfoService) {
        this.appUserManageService = appUserManageService;
        this.appUserBaseInfoService = appUserBaseInfoService;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(AppUserDetails.class, user, "only supports AppUserDetails.class");
        AppUserDetails appUserDetails = (AppUserDetails) user;
        AppUserBaseInfo appUserBaseInfo = appUserDetails.getAppUserBaseInfo();
        this.appUserManageService.createUser(appUserDetails);
        AppUserReference savedUser = this.appUserManageService.getAppUserReferenceService().getOneByUsername(appUserDetails.getUsername());
        AppUserBaseInfo build = AppUserBaseInfo.uncreated(savedUser.getUserId())
                .whereFrom(appUserBaseInfo.getCountry(), appUserBaseInfo.getProvince(), appUserBaseInfo.getCity())
                .withAvatar(appUserBaseInfo.getAvatar())
                .withBirth(appUserBaseInfo.getBirth())
                .withGender(appUserBaseInfo.getGender())
                .withNickname(appUserBaseInfo.getNickname())
                .build();
        this.appUserBaseInfoService.save(build);
    }

    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(AppUserDetails.class, user, "only supports AppUserDetails.class");
        AppUserDetails appUserDetails = (AppUserDetails) user;
        AppUserBaseInfo appUserBaseInfo = appUserDetails.getAppUserBaseInfo();
        this.appUserManageService.updateUser(appUserDetails);
        AppUserBaseInfo userBaseInfo = this.appUserBaseInfoService.getByUserId(appUserDetails.getId());
        AppUserBaseInfo build = AppUserBaseInfo.created(userBaseInfo.getId(), userBaseInfo.getUserId())
                .whereFrom(appUserBaseInfo.getCountry(), appUserBaseInfo.getProvince(), appUserBaseInfo.getCity())
                .withAvatar(appUserBaseInfo.getAvatar())
                .withBirth(appUserBaseInfo.getBirth())
                .withGender(appUserBaseInfo.getGender())
                .withNickname(appUserBaseInfo.getNickname())
                .build();
        this.appUserBaseInfoService.updateById(build);
    }

    @Override
    public void deleteUser(String username) {
        this.appUserManageService.deleteUser(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        this.appUserManageService.changePassword(oldPassword, newPassword);
    }

    @Override
    public boolean userExists(String username) {
        return this.appUserManageService.userExists(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.appUserManageService.loadUserByUsername(username);
    }

    public AppUserManageService getAppUserManageService() {
        return appUserManageService;
    }

    public void setAppUserManageService(AppUserManageService appUserManageService) {
        this.appUserManageService = appUserManageService;
    }

    public AppUserBaseInfoService getAppUserBaseInfoService() {
        return appUserBaseInfoService;
    }

    public void setAppUserBaseInfoService(AppUserBaseInfoService appUserBaseInfoService) {
        this.appUserBaseInfoService = appUserBaseInfoService;
    }

    public MessageSourceAccessor getMessages() {
        return messages;
    }

    public void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }
}
