package com.mtfm.app_support.service.user;

import com.mtfm.app_support.service.AppUserBaseInfoService;
import com.mtfm.app_support.service.provisioning.AppUser;
import com.mtfm.app_support.service.provisioning.AppUserDetails;
import com.mtfm.wechat_mp.authentication.CreateUser;
import com.mtfm.wechat_mp.authentication.MpUser;
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
        AppUserDetails appUserDetails = new AppUserDetails();
        super.createUser(appUserDetails);
    }

    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(CreateUser.class, user, "only supports CreateUser.class");
        // 将小程序用户转为项目的用户
        CreateUser createUser = (CreateUser) user;
        AppUserDetails appUserDetails = new AppUserDetails();
        super.updateUser(appUserDetails);
    }
}
