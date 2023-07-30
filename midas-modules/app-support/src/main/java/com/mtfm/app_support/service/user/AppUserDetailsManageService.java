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

import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.mapper.AppUserMapper;
import com.mtfm.app_support.service.AppUserBaseInfoService;
import com.mtfm.app_support.service.AppUserReferenceService;
import com.mtfm.app_support.service.AppUserSecretService;
import com.mtfm.app_support.service.provisioning.AppUserDetails;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户基本信息业务
 * 该类是{@link AppUserAccountManageService}的代理类，其目的主要是用户在更上层进行更新用户的基本信息
 */
@Transactional(rollbackFor = Exception.class)
public class AppUserDetailsManageService extends AppUserAccountManageService implements MessageSourceAware {

    private final AppUserBaseInfoService appUserBaseInfoService;

    public AppUserDetailsManageService(AppUserMapper appUserMapper,
                                       AppUserReferenceService appUserReferenceService,
                                       AppUserSecretService appUserSecretService,
                                       AppUserBaseInfoService appUserBaseInfoService) {
        super(appUserMapper, appUserReferenceService, appUserSecretService);
        this.appUserBaseInfoService = appUserBaseInfoService;
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(AppUserDetails.class, user, "only supports AppUserDetails.class");
        AppUserDetails appUserDetails = (AppUserDetails) user;
        AppUserBaseInfo appUserBaseInfo = appUserDetails.getAppUserBaseInfo();
        super.createUser(user);
        AppUserReference savedUser = this.getAppUserReferenceService().getOneByUsername(appUserDetails.getUsername());
        AppUserBaseInfo build = AppUserBaseInfo.uncreated(savedUser.getUserId())
                .whereFrom(appUserBaseInfo.getCountry(), appUserBaseInfo.getProvince(), appUserBaseInfo.getCity())
                .withAvatar(appUserBaseInfo.getAvatar())
                .withBirth(appUserBaseInfo.getBirth())
                .withGender(appUserBaseInfo.getGender())
                .withNickname(appUserBaseInfo.getNickname())
                .build();
        this.appUserBaseInfoService.createAppUser(build);

    }

    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(AppUserDetails.class, user, "only supports AppUserDetails.class");
        AppUserDetails appUserDetails = (AppUserDetails) user;
        AppUserBaseInfo appUserBaseInfo = appUserDetails.getAppUserBaseInfo();
        super.updateUser(user);
        AppUserBaseInfo userBaseInfo = this.appUserBaseInfoService.getByUserId(appUserDetails.getId());
        AppUserBaseInfo build = AppUserBaseInfo.created(userBaseInfo.getId(), userBaseInfo.getUserId())
                .whereFrom(appUserBaseInfo.getCountry(), appUserBaseInfo.getProvince(), appUserBaseInfo.getCity())
                .withAvatar(appUserBaseInfo.getAvatar())
                .withBirth(appUserBaseInfo.getBirth())
                .withGender(appUserBaseInfo.getGender())
                .withNickname(appUserBaseInfo.getNickname())
                .build();
        this.appUserBaseInfoService.updateAppUser(build);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        super.setMessageSource(messageSource);
    }

    public AppUserBaseInfoService getAppUserBaseInfoService() {
        return appUserBaseInfoService;
    }
}
