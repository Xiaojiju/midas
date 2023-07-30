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
package com.mtfm.app_support.service.user;

import com.mtfm.app_support.AppSupportIdentifier;
import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.service.AppUserBaseInfoService;
import com.mtfm.app_support.service.provisioning.AppUserDetails;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityCode;
import com.mtfm.wechat_mp.authentication.MpUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 微信小程序认证代理适配器
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MiniProgramUserProxyAdapter extends AppUserInfoDetailsService {

    public MiniProgramUserProxyAdapter(AppUserAccountManageService appUserAccountManageService, AppUserBaseInfoService appUserBaseInfoService) {
        super(appUserAccountManageService, appUserBaseInfoService);
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(MpUserDetails.class, user, "only supports MpUserDetails.class");
        // 将小程序用户转为项目的用户
        MpUserDetails registry = (MpUserDetails) user;
        AppUserBaseInfo appUserBaseInfo = AppUserBaseInfo.uncreated()
                .whereFrom(registry.getCountry(), registry.getProvince(), registry.getCity())
                .withAvatar(registry.getAvatarUrl())
                .withGender(registry.getGender())
                .withNickname(registry.getNickName())
                .build();
        AppUser appUser = AppUser.builder(registry.getUsername(), AppSupportIdentifier.PHONE)
                .usedSecret(false)
                .thirdPart(true)
                .build();
        AppUserDetails appUserDetails = new AppUserDetails(appUserBaseInfo, appUser);
        super.createUser(appUserDetails);
    }

    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(MpUserDetails.class, user, "only supports MpUserDetails.class");
        // 将小程序用户转为项目的用户
        MpUserDetails createUser = (MpUserDetails) user;
        AppUserReference oneByUsername = this.getAppUserManageService().getAppUserReferenceService().getOneByUsername(createUser.getUsername());
        if (oneByUsername == null) {
            throw new ServiceException(this.getMessages().getMessage("AppUserDetailsService.userNotFound"), SecurityCode.USER_NOT_FOUND.getCode());
        }
        AppUserBaseInfo baseInfo = this.getAppUserBaseInfoService().getByUserId(oneByUsername.getUserId());
        AppUserBaseInfo appUserBaseInfo = AppUserBaseInfo.created(baseInfo.getId(), baseInfo.getUserId())
                .whereFrom(createUser.getCountry(), createUser.getProvince(), createUser.getCity())
                .withAvatar(createUser.getAvatarUrl())
                .withGender(createUser.getGender())
                .withNickname(createUser.getNickName())
                .build();
        AppUser appUser = AppUser.builder(createUser.getUsername(), AppSupportIdentifier.PHONE)
                .usedSecret(false)
                .thirdPart(true)
                .build();
        AppUserDetails appUserDetails = new AppUserDetails(appUserBaseInfo, appUser);
        super.updateUser(appUserDetails);
    }
}
