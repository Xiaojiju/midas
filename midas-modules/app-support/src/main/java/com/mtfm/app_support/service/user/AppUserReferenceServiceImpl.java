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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.app_support.AppSupportCode;
import com.mtfm.app_support.config.AppSupportMessageSource;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.mapper.AppUserReferenceMapper;
import com.mtfm.app_support.service.AppUserReferenceService;
import com.mtfm.core.context.exceptions.ServiceException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户用户名管理
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserReferenceServiceImpl extends ServiceImpl<AppUserReferenceMapper, AppUserReference> implements AppUserReferenceService, MessageSourceAware {

    private MessageSourceAccessor messages = AppSupportMessageSource.getAccessor();

    @Override
    public void createAppUserUsername(AppUserReference appUserReference) {
        AppUserReference oneByUserIdentifier = this.getOneByUserIdentifier(appUserReference.getUserId(), appUserReference.getIdentifier());
        if (oneByUserIdentifier == null) {
            this.save(appUserReference);
            return ;
        }
        // 如果用户名已经存在且为同一用户则不进行操作
        if (oneByUserIdentifier.getUsername().equals(appUserReference.getUsername())) {
            return ;
        }
        throw new ServiceException(this.messages.getMessage("AppUserReferenceService.authenticationMethod",
                "The same username already exists for other authentication methods and cannot be added again"),
                AppSupportCode.UPDATE_USERNAME_EXIST_FOR_OTHER_AUTHENTICATION_METHOD.getCode());
    }

    @Override
    public void updateAppUserUsername(AppUserReference appUserReference) {
        AppUserReference userReference = this.getOneByUsername(appUserReference.getUsername());
        if (userReference == null) {
            this.updateById(appUserReference);
        }
        if (userReference != null) {
            if (!userReference.getUserId().equals(appUserReference.getUserId())) {
                throw new ServiceException(this.messages.getMessage("AppUserReferenceService.usernameExist",
                        "username had exist"),
                        AppSupportCode.USERNAME_NAME_EXIST.getCode());
            }
            if (!userReference.getIdentifier().equals(appUserReference.getIdentifier())) {
                throw new ServiceException(this.messages.getMessage("AppUserReferenceService.authenticationMethod",
                        "The same username already exists for other authentication methods and cannot be added again"),
                        AppSupportCode.UPDATE_USERNAME_EXIST_FOR_OTHER_AUTHENTICATION_METHOD.getCode());
            }
        }
    }

    @Override
    public AppUserReference getOneByUsername(String username) {
        return this.getOne(
                new QueryWrapper<AppUserReference>().lambda().eq(AppUserReference::getUsername, username)
        );
    }

    @Override
    public AppUserReference getOneByUserIdentifier(@NotNull String userId,@NotNull String identifier) {
        return this.getOne(
                new QueryWrapper<AppUserReference>().lambda()
                        .eq(AppUserReference::getUserId, userId)
                        .eq(AppUserReference::getIdentifier, identifier)
        );
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
