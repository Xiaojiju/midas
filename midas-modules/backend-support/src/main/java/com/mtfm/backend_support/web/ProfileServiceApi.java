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

@RestController
@RequestMapping("/solar/api/v1")
public class ProfileServiceApi {

    private UserInformationManageService userManage;
    private UserBaseInfoManager userBaseInfoManager;

    public ProfileServiceApi(UserInformationManageService userManage) {
        this.userManage = userManage;
    }

    @PostMapping("/profile/changePwd")
    public RestResult<Object> changePassword(@RequestBody @Validated(ValidateGroup.Update.class) ChangePassword changePassword) {
        this.userManage.changePassword(changePassword.getOldPassword(), changePassword.getNewPassword());
        return RestResult.success();
    }

    @GetMapping("/profile")
    public RestResult<UserInformation> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInformation information = this.userManage.getInformation((String) authentication.getPrincipal());
        return RestResult.success(information);
    }

    @PutMapping("/profile")
    public RestResult<Object> updateUserInfo(@RequestBody SolarBaseInfo baseInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SolarBaseInfo solarBaseInfo = userBaseInfoManager.getByUserId((String) authentication.getPrincipal());
        baseInfo.setId(solarBaseInfo.getId());
        baseInfo.setuId((String) authentication.getPrincipal());
        this.userBaseInfoManager.updateById(baseInfo);
        return RestResult.success();
    }

}
