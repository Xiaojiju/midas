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
package com.mtfm.app_support.web;

import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.app_support.entity.AppUserSecret;
import com.mtfm.app_support.service.AppUserBaseInfoService;
import com.mtfm.app_support.service.AppUserSecretService;
import com.mtfm.app_support.service.user.AppUserAccountManageService;
import com.mtfm.app_support.web.body.Password;
import com.mtfm.app_support.web.response.PasswordExist;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityHolder;
import com.mtfm.wechat_mp.authentication.MpUser;
import org.springframework.web.bind.annotation.*;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户信息详情API
 */
@RestController
@RequestMapping("/solar/api/v1")
public class AppUserDetailsApi {

    private AppUserBaseInfoService appUserBaseInfoService;

    private AppUserAccountManageService appUserAccountManageService;

    private AppUserSecretService appUserSecretService;

    public AppUserDetailsApi(AppUserBaseInfoService appUserBaseInfoService,
                             AppUserAccountManageService appUserAccountManageService,
                             AppUserSecretService appUserSecretService) {
        this.appUserBaseInfoService = appUserBaseInfoService;
        this.appUserAccountManageService = appUserAccountManageService;
        this.appUserSecretService = appUserSecretService;
    }

    /**
     * 获取用户基本信息
     * @return 用户基本信息
     */
    @GetMapping("/user")
    public RestResult<MpUser> getUserDetails() {
        AppUser appUser = (AppUser) SecurityHolder.getPrincipal();
        AppUserBaseInfo appUserBaseInfo = this.appUserBaseInfoService.getByUserId(appUser.getId());
        MpUser mpUser = MpUser.builder()
                .withNickname(appUserBaseInfo.getNickname())
                .withAvatar(appUserBaseInfo.getAvatar())
                .withGender(appUserBaseInfo.getGender())
                .from(appUserBaseInfo.getCountry(), appUserBaseInfo.getProvince(), appUserBaseInfo.getCity())
                .build();
        return RestResult.success(mpUser);
    }

    /**
     * 修改用户基本信息
     * @param appUserBaseInfo 用户基本信息
     */
    @PutMapping("/user")
    public RestResult<Void> updateBaseInfo(@RequestBody MpUser mpUser) {
        AppUser appUser = (AppUser) SecurityHolder.getPrincipal();
        AppUserBaseInfo appUserBaseInfo = this.appUserBaseInfoService.getByUserId(appUser.getId());
        appUserBaseInfo = AppUserBaseInfo.created(appUserBaseInfo.getId(), appUserBaseInfo.getUserId())
                .whereFrom(mpUser.getCountry(), mpUser.getProvince(), mpUser.getCity())
                .withNickname(mpUser.getNickName())
                .withAvatar(mpUser.getAvatarUrl())
                .withGender(mpUser.getGender())
                .build();
        this.appUserBaseInfoService.updateAppUser(appUserBaseInfo);
        return RestResult.success();
    }

    /**
     * 修改密码
     * @param password 密码
     */
    @PostMapping("/user/pwd")
    public RestResult<Void> changePassword(@RequestBody Password password) {
        this.appUserAccountManageService.changePassword(password.getOldPassword(), password.getNewPassword());
        return RestResult.success();
    }

    /**
     * 获取用户是否已经创建了密码
     * @return {@link PasswordExist}
     */
    @GetMapping("/user/pwd")
    public RestResult<PasswordExist> secretExist() {
        AppUserSecret appUserSecret = this.appUserSecretService.getByUserId();
        return RestResult.success(PasswordExist.exist(appUserSecret != null));
    }


    protected AppUserBaseInfoService getAppUserBaseInfoService() {
        return appUserBaseInfoService;
    }

    public void setAppUserBaseInfoService(AppUserBaseInfoService appUserBaseInfoService) {
        this.appUserBaseInfoService = appUserBaseInfoService;
    }

    protected AppUserAccountManageService getAppUserManageService() {
        return appUserAccountManageService;
    }

    public void setAppUserManageService(AppUserAccountManageService appUserAccountManageService) {
        this.appUserAccountManageService = appUserAccountManageService;
    }

    protected AppUserSecretService getAppUserSecretService() {
        return appUserSecretService;
    }

    public void setAppUserSecretService(AppUserSecretService appUserSecretService) {
        this.appUserSecretService = appUserSecretService;
    }
}
