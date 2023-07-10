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
package com.mtfm.security.authentication;

import com.mtfm.security.AppUser;
import com.mtfm.security.context.SolarMessageSource;
import com.mtfm.tools.enums.Judge;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;

/**
 * 用户认证信息校验
 * @author 一块小饼干
 * @since 1.0.0
 */
public class AppUserPreAuthenticationChecks implements UserDetailsChecker, MessageSourceAware, InitializingBean {

    protected final Log logger = LogFactory.getLog(getClass());

    private MessageSourceAccessor messages = SolarMessageSource.getAccessor();

    private final boolean enableAccountExpired;

    private final boolean enableUsernameExpired;

    /**
     * 启动默认配置检验
     */
    public AppUserPreAuthenticationChecks() {
        this(false, false);
    }

    /**
     * 引入自定义国际化消息
     * @param enableAccountExpired 账号过期
     * @param enableUsernameExpired 当前认证方式过期
     * @param enableCredentialExpired 允许密钥过期
     */
    public AppUserPreAuthenticationChecks(boolean enableAccountExpired, boolean enableUsernameExpired) {
        this.enableAccountExpired = enableAccountExpired;
        this.enableUsernameExpired = enableUsernameExpired;
    }

    @Override
    public void check(UserDetails user) {
        Assert.isInstanceOf(AppUser.class, user, "only supposed AppUser.class");
        AppUser appUser = (AppUser) user;
        this.accountCheck(appUser);
        this.usernameCheck(appUser);
    }

    private void accountCheck(AppUser user) {
        if (!user.isAccountNonLocked()) {
            AppUserPreAuthenticationChecks.this.logger
                    .debug("Failed to authenticate since user account is locked");
            throw new LockedException(AppUserPreAuthenticationChecks.this.messages
                    .getMessage("UserTemplatePreAuthenticationChecks.locked", "User account is locked"));
        }
        if (enableAccountExpired && !user.isAccountNonExpired()) {
            AppUserPreAuthenticationChecks.this.logger
                    .debug("Failed to authenticate since user account has expired");
            throw new AccountExpiredException(AppUserPreAuthenticationChecks.this.messages
                    .getMessage("UserTemplatePreAuthenticationChecks.expired", "User account has expired"));
        }
    }

    private void usernameCheck(AppUser user) {
        if (enableUsernameExpired && !user.isEnabled()) {
            AppUserPreAuthenticationChecks.this.logger.debug("Failed to authenticate since username expired");
            throw new AccountExpiredException(AppUserPreAuthenticationChecks.this.messages
                    .getMessage("UserTemplatePreAuthenticationChecks.usernameExpired", "username has expired"));
        }
        if (user.getLoginAccess() != null && user.getLoginAccess() != Judge.YES) {
            AppUserPreAuthenticationChecks.this.logger.debug("Failed to authenticate since username locked");
            throw new AccountExpiredException(AppUserPreAuthenticationChecks.this.messages
                    .getMessage("UserTemplatePreAuthenticationChecks.usernameLocked", "username has locked"));
        }
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "MessageSource could not be null");
    }

}
