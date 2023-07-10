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

import com.mtfm.backend_support.service.provisioning.UserInformation;
import com.mtfm.backend_support.service.query.ValuePageQuery;
import com.mtfm.backend_support.service.user.UserInformationManageService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.Target;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.core.util.validator.ValidateGroup;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户操作api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class UserServiceApi implements MessageSourceAware {

    private UserInformationManageService userManage;

    private MessageSourceAccessor messageSource;

    public UserServiceApi(UserInformationManageService userManage) {
        this.userManage = userManage;
    }

    /**
     * 创建用户
     * @param userInformation 用户详细信息
     * @return message中为OK则为成功，其他则失败
     */
    @PostMapping("/user")
    public RestResult<Object> createUser(@RequestBody @Validated({ValidateGroup.Create.class}) UserInformation userInformation) {
        this.userManage.createUser(userInformation);
        return RestResult.success();
    }

    /**
     * 修改用户
     * @param userInformation 用户详细信息
     * @return message中为OK则为成功，其他则失败
     */
    @PutMapping("/user")
    public RestResult<Object> updateUser(@RequestBody @Validated({ValidateGroup.Update.class})  UserInformation userInformation) {
        this.userManage.updateUser(userInformation);
        return RestResult.success();
    }

    /**
     * 删除用户
     * @param target 指定用户id
     * @return message中为OK则为成功，其他则失败
     */
    @DeleteMapping("/user")
    public RestResult<Object> removeUser(@RequestBody Target<String> target) {
        this.userManage.deleteUser(target.getTarget());
        return RestResult.success();
    }

    /**
     * 获取用户信息
     * @param target 指定用户id
     * @return 用户详细信息
     */
    @GetMapping("/user")
    public RestResult<UserInformation> getUser(Target<String> target) {
        UserInformation userInformation = this.userManage.getInformation(target.getTarget());
        if (userInformation == null) {
            return RestResult.fail(this.messageSource.getMessage("UserDetailsManager.nonExist"));
        }
        return RestResult.success(userInformation);
    }

    /**
     * 用户分页
     * @param valuePageQuery 关键词分页
     * @return 用户详细信息
     */
    @GetMapping("/users")
    public RestResult<PageTemplate<UserInformation>> getUsers(ValuePageQuery valuePageQuery) {
        return RestResult.success(this.userManage.pageList(valuePageQuery));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    public UserInformationManageService getUserManage() {
        return userManage;
    }

    public void setUserManage(UserInformationManageService userManage) {
        this.userManage = userManage;
    }
}
