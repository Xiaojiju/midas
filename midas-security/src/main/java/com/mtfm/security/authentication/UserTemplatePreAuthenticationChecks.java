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

import com.mtfm.security.UserTemplate;
import com.mtfm.security.config.WebSecurityProperties;
import com.mtfm.security.context.SolarMessageSource;
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
public class UserTemplatePreAuthenticationChecks implements UserDetailsChecker, MessageSourceAware, InitializingBean {

    protected final Log logger = LogFactory.getLog(getClass());
    private MessageSourceAccessor messages;
    private boolean enableAccountExpired;
    private boolean enableMethodExpired;

    /**
     * 默认构造函数，默认允许账号过期和认证方式过期。
     * 国际化消息处理设置为默认{@link SolarMessageSource#getAccessor()}
     */
    public UserTemplatePreAuthenticationChecks() {
        this(SolarMessageSource.getAccessor(), true, true);
    }

    /**
     * 引入配置文件，账号过期和认证方式过期跟随配置文件进行配置
     * 国际化消息处理设置为默认{@link SolarMessageSource#getAccessor()}
     * @param webSecurityProperties 配置文件
     */
    public UserTemplatePreAuthenticationChecks(WebSecurityProperties webSecurityProperties) {
        this(SolarMessageSource.getAccessor(),
                webSecurityProperties.isEnableAccountExpired(),
                webSecurityProperties.isEnableMethodExpired());
    }

    /**
     * 引入默认国际化消息类和外部{@link WebSecurityProperties}配置文件
     * @param messages 自定义国际化消息处理
     * @param webSecurityProperties 自定义配置文件
     */
    public UserTemplatePreAuthenticationChecks(MessageSourceAccessor messages,
                                               WebSecurityProperties webSecurityProperties) {
        this(messages, webSecurityProperties.isEnableAccountExpired(), webSecurityProperties.isEnableMethodExpired());
    }

    /**
     * 引入自定义国际化消息
     * @param messages 自定义国际化消息
     * @param enableAccountExpired 账号过期
     * @param enableMethodExpired 当前认证方式过期
     */
    public UserTemplatePreAuthenticationChecks(MessageSourceAccessor messages,
                                               boolean enableAccountExpired,
                                               boolean enableMethodExpired) {
        this.messages = messages;
        this.enableAccountExpired = enableAccountExpired;
        this.enableMethodExpired = enableMethodExpired;
    }

    @Override
    public void check(UserDetails user) {
        Assert.isInstanceOf(UserTemplate.class, user, "only supposed UserTemplate.class");
        UserTemplate userTemplate = (UserTemplate) user;
        AccountCheck(userTemplate);
        currentMethodChecks(userTemplate);
    }

    private void AccountCheck(UserTemplate user) {
        if (!user.isAccountNonLocked() || !user.isEnabled()) {
            UserTemplatePreAuthenticationChecks.this.logger
                    .debug("Failed to authenticate since user account is locked");
            throw new LockedException(UserTemplatePreAuthenticationChecks.this.messages
                    .getMessage("UserTemplatePreAuthenticationChecks.locked", "User account is locked"));
        }
        if (enableAccountExpired && !user.isAccountNonExpired()) {
            UserTemplatePreAuthenticationChecks.this.logger
                    .debug("Failed to authenticate since user account has expired");
            throw new AccountExpiredException(UserTemplatePreAuthenticationChecks.this.messages
                    .getMessage("UserTemplatePreAuthenticationChecks.expired", "User account has expired"));
        }
    }

    private void currentMethodChecks(UserTemplate user) {
        if (!user.isCurrentMethodNonLocked()) {
            UserTemplatePreAuthenticationChecks.this.logger.debug("Failed to authenticate since user current method locked");
            throw new AccountExpiredException(UserTemplatePreAuthenticationChecks.this.messages
                    .getMessage("UserTemplatePreAuthenticationChecks.currentMethod.locked", "User current method has locked"));
        }
        if (enableMethodExpired && !user.isCurrentMethodNonExpired()) {
            UserTemplatePreAuthenticationChecks.this.logger.debug("Failed to authenticate since user current method expired");
            throw new AccountExpiredException(UserTemplatePreAuthenticationChecks.this.messages
                    .getMessage("UserTemplatePreAuthenticationChecks.currentMethod.expired", "User current method has expired"));
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

    public boolean isEnableAccountExpired() {
        return enableAccountExpired;
    }

    public void setEnableAccountExpired(boolean enableAccountExpired) {
        this.enableAccountExpired = enableAccountExpired;
    }

    public boolean isEnableMethodExpired() {
        return enableMethodExpired;
    }

    public void setEnableMethodExpired(boolean enableMethodExpired) {
        this.enableMethodExpired = enableMethodExpired;
    }
}
