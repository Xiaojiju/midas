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
import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.app_support.mapper.AppUserBaseInfoMapper;
import com.mtfm.app_support.service.AppUserBaseInfoService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.SecurityHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户基本信息业务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserBaseInfoServiceImpl extends ServiceImpl<AppUserBaseInfoMapper, AppUserBaseInfo> implements AppUserBaseInfoService, MessageSourceAware {

    private MessageSourceAccessor messages = AppSupportMessageSource.getAccessor();

    @Override
    public AppUserBaseInfo getByUserId(String userId) {
        AppUserBaseInfo one = this.getOne(new QueryWrapper<AppUserBaseInfo>().lambda()
                .eq(AppUserBaseInfo::getUserId, userId));
        if (one == null) {
            throw new ServiceException(this.messages.getMessage("AppUserDetailsService.userNotFoundByUserId",
                    "could not found app user details"),
                    AppSupportCode.USER_NOT_FOUND.getCode());
        }
        return one;
    }

    @Override
    public void createAppUser(AppUserBaseInfo appUserBaseInfo) {
        this.save(appUserBaseInfo);
    }

    @Override
    public void updateAppUser(AppUserBaseInfo appUserBaseInfo) {
        String userId = (String) SecurityHolder.getPrincipal();
        AppUserBaseInfo baseInfo = this.getByUserId(userId);
        if (baseInfo == null) {
            throw new ServiceException(this.messages.getMessage("AppUserDetailsService.userNotFoundByUserId",
                    "could not found app user details"),
                    AppSupportCode.USER_NOT_FOUND.getCode());
        }
        appUserBaseInfo.setId(baseInfo.getId());
        appUserBaseInfo.setUserId(baseInfo.getUserId());
        this.updateById(appUserBaseInfo);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
