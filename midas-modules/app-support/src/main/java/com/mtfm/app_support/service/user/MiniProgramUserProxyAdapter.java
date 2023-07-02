package com.mtfm.app_support.service.user;

import com.mtfm.app_support.AppSupportIdentifier;
import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.service.AppUserBaseInfoService;
import com.mtfm.app_support.service.provisioning.AppUserDetails;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityCode;
import com.mtfm.wechat_mp.authentication.CreateUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class MiniProgramUserProxyAdapter extends AppUserInfoDetailsService {

    public MiniProgramUserProxyAdapter(AppUserManageService appUserManageService, AppUserBaseInfoService appUserBaseInfoService) {
        super(appUserManageService, appUserBaseInfoService);
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(CreateUser.class, user, "only supports CreateUser.class");
        // 将小程序用户转为项目的用户
        CreateUser createUser = (CreateUser) user;
        AppUserBaseInfo appUserBaseInfo = AppUserBaseInfo.uncreated()
                .whereFrom(createUser.getCountry(), createUser.getProvince(), createUser.getCity())
                .withAvatar(createUser.getAvatar())
                .withGender(createUser.getGender())
                .withNickname(createUser.getNickname())
                .build();
        AppUser appUser = AppUser.builder(createUser.getUsername(), AppSupportIdentifier.MINI_PROGRAM)
                .usedSecret(false)
                .thirdPart(true)
                .build();
        AppUserDetails appUserDetails = new AppUserDetails(appUserBaseInfo, appUser);
        super.createUser(appUserDetails);
    }

    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(CreateUser.class, user, "only supports CreateUser.class");
        // 将小程序用户转为项目的用户
        CreateUser createUser = (CreateUser) user;
        AppUserReference oneByUsername = this.getAppUserManageService().getAppUserReferenceService().getOneByUsername(createUser.getUsername());
        if (oneByUsername == null) {
            throw new ServiceException(this.getMessages().getMessage("AppUserDetailsService.userNotFound"), SecurityCode.USER_NOT_FOUND.getCode());
        }
        AppUserBaseInfo baseInfo = this.getAppUserBaseInfoService().getByUserId(oneByUsername.getUserId());
        AppUserBaseInfo appUserBaseInfo = AppUserBaseInfo.created(baseInfo.getId(), baseInfo.getUserId())
                .whereFrom(createUser.getCountry(), createUser.getProvince(), createUser.getCity())
                .withAvatar(createUser.getAvatar())
                .withGender(createUser.getGender())
                .withNickname(createUser.getNickname())
                .build();
        AppUser appUser = AppUser.builder(createUser.getUsername(), AppSupportIdentifier.MINI_PROGRAM)
                .usedSecret(false)
                .thirdPart(true)
                .build();
        AppUserDetails appUserDetails = new AppUserDetails(appUserBaseInfo, appUser);
        super.updateUser(appUserDetails);
    }
}
