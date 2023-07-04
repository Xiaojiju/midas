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

import com.mtfm.app_support.mapper.AppUserMapper;
import com.mtfm.security.AppUser;
import com.mtfm.security.config.WebAutoSecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户账号业务
 */
@Service
public class AppUserDetailsService implements UserDetailsService, MessageSourceAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);
    private AppUserMapper appUserMapper;
    private MessageSourceAccessor messages;

    /**
     * 默认关闭权限，如果需要开启，在注入容器前，需要在获取 {@link WebAutoSecurityConfiguration#isEnablePermissions()}的值进行动态配置
     */
    private boolean enablePermissions = false;
    private static final String NONE_PASSWORD = "NONE";

    public AppUserDetailsService(AppUserMapper appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.appUserMapper.selectUserByReferenceKey(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(this.messages.getMessage("AppUserDetailsService.userNotFound",
                    new Object[] { username },
                    "could not found app user by username{0}"));
        }
        appUser.setPassword(NONE_PASSWORD);
        return appUser;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(messages, "A message source could not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public AppUserMapper getAppUserMapper() {
        return appUserMapper;
    }

    public void setAppUserMapper(AppUserMapper appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    protected MessageSourceAccessor getMessages() {
        return messages;
    }

    protected void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }

    protected boolean isEnablePermissions() {
        return enablePermissions;
    }

    protected void setEnablePermissions(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }
}
