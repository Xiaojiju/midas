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
package com.mtfm.backend_support.web;

import com.mtfm.backend_support.entity.SolarBaseInfo;
import com.mtfm.backend_support.service.UserBaseInfoManager;
import com.mtfm.backend_support.service.provisioning.UserInformation;
import com.mtfm.backend_support.service.user.UserInformationManageService;
import com.mtfm.backend_support.web.params.ChangePassword;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.validator.ValidateGroup;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 个人信息api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class ProfileServiceApi {

    private UserInformationManageService userManage;

    private UserBaseInfoManager userBaseInfoManager;

    public ProfileServiceApi(UserInformationManageService userManage, UserBaseInfoManager userBaseInfoManager) {
        this.userManage = userManage;
        this.userBaseInfoManager = userBaseInfoManager;
    }

    /**
     * 修改密码
     * @param changePassword 老密码和新密码{@link ChangePassword}
     * @return message中为OK则为成功，其他则失败
     */
    @PostMapping("/profile/changePwd")
    public RestResult<Object> changePassword(@RequestBody @Validated(ValidateGroup.Update.class) ChangePassword changePassword) {
        this.userManage.changePassword(changePassword.getOldPassword(), changePassword.getNewPassword());
        return RestResult.success();
    }

    /**
     * 获取个人信息
     * @return {@link UserInformation}个人信息
     */
    @GetMapping("/profile")
    public RestResult<UserInformation> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInformation information = this.userManage.getInformation((String) authentication.getPrincipal());
        return RestResult.success(information);
    }

    /**
     * 修改个人信息
     * @param baseInfo {@link SolarBaseInfo}仅仅只能修改基本信息，如需要修改其他的信息，需要使用{@link UserServiceApi#updateUser(UserInformation)}
     * @return message中为OK则为成功，其他则失败
     */
    @PutMapping("/profile")
    public RestResult<Object> updateUserInfo(@RequestBody SolarBaseInfo baseInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SolarBaseInfo solarBaseInfo = userBaseInfoManager.getByUserId((String) authentication.getPrincipal());
        baseInfo.setId(solarBaseInfo.getId());
        baseInfo.setuId((String) authentication.getPrincipal());
        this.userBaseInfoManager.updateById(baseInfo);
        return RestResult.success();
    }

    public UserInformationManageService getUserManage() {
        return userManage;
    }

    public void setUserManage(UserInformationManageService userManage) {
        this.userManage = userManage;
    }

    public UserBaseInfoManager getUserBaseInfoManager() {
        return userBaseInfoManager;
    }

    public void setUserBaseInfoManager(UserBaseInfoManager userBaseInfoManager) {
        this.userBaseInfoManager = userBaseInfoManager;
    }
}
