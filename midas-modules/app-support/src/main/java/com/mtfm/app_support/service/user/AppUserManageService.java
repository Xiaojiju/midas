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

import com.mtfm.app_support.AppSupportCode;
import com.mtfm.app_support.entity.AppAccount;
import com.mtfm.app_support.entity.AppUserReference;
import com.mtfm.app_support.entity.AppUserSecret;
import com.mtfm.app_support.mapper.AppUserMapper;
import com.mtfm.app_support.service.AppUserReferenceService;
import com.mtfm.app_support.service.AppUserSecretService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityCode;
import com.mtfm.security.SecurityHolder;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户账号管理
 * 该类主要是为更新用户的账号信息，如果需要一同更新用户的基本信息，需要使用代理类{@link AppUserInfoDetailsService}
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserManageService extends AppUserDetailsService implements UserDetailsManager, InitializingBean, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(AppUserManageService.class);
    private MessageSourceAccessor messages;
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private AppUserReferenceService appUserReferenceService;
    private AppUserSecretService appUserSecretService;

    public AppUserManageService(AppUserMapper appUserMapper, AppUserReferenceService appUserReferenceService, AppUserSecretService appUserSecretService) {
        super(appUserMapper);
        this.appUserReferenceService = appUserReferenceService;
        this.appUserSecretService = appUserSecretService;
    }

    /**
     * 创建用户
     * @param user 用户账号信息，只支持{@link AppUser}或其子类
     */
    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(AppUser.class, user, "only supports AppUser.class");
        AppUser appUser = (AppUser) user;
        // 优先添加用户账号
        boolean nonLocked = appUser.isAccountNonLocked();
        AppAccount appAccount = AppAccount.uncreated(nonLocked ? Judge.NO : Judge.YES, appUser.getAccountExpiredTime());
        super.getAppUserMapper().insert(appAccount);

        // 其次进行创建用户的用户名
        AppUserReference existUsername = this.appUserReferenceService.getOneByUsername(appUser.getUsername());
        if (existUsername != null) {
            throw new ServiceException(this.messages.getMessage("UserDetailsManager.hadExist"), SecurityCode.USERNAME_EXIST.getCode());
        }
        String userId = appAccount.getId();
        AppUserReference userReference = AppUserReference.builder(userId).withUsername(appUser.getUsername(), appUser.getIdentifier())
                .enableLogin(true).enableUsingSecretAuthenticated(appUser.getSecretAccess() == Judge.YES)
                .isThirdPart(appUser.getThirdPart() == Judge.YES).validate(appUser.getValidated() == Judge.YES)
                .expiredAt(appUser.getUsernameExpiredTime())
                .withAdditionalKey(appUser.getAdditionalKey()).build();
        appUserReferenceService.save(userReference);

        // 最后创建密码，如果密码不存在，又为第三方认证，则不进行创建密码
        String password = appUser.getPassword();
        if (!StringUtils.hasText(password)) {
            if (appUser.getThirdPart() == Judge.YES) {
                return ;
            } else {
                throw new ServiceException(this.messages.getMessage("AppUserDetailsService.nonPassword",
                        "password must not be null or empty while create user"),
                        AppSupportCode.NONE_PASSWORD.getCode());
            }
        }
        AppUserSecret userSecret = AppUserSecret.builder(userId)
                .makeItSecret(appUser.getPassword(), "", passwordEncoder)
                .build();
        this.appUserSecretService.save(userSecret);
    }

    /**
     * App用户只能更新账号信息[表设计为所有的手机邮箱等等关联信息都可以为账号]
     * @param user 用户账号信息，只支持{@link AppUser}或其子类
     */
    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(AppUser.class, user, "only supports AppUser.class");
        AppUser appUser = (AppUser) user;
        String userId = appUser.getId();
        AppUserReference appUserReference = this.appUserReferenceService.getOneByUsername(appUser.getUsername());
        if (appUserReference != null) {
            if (!appUserReference.getUserId().equals(userId)) {
                throw new ServiceException(this.messages.getMessage("UserDetailsManager.hadExist"),
                        SecurityCode.USERNAME_EXIST.getCode());
            } else {
                // 如果为当前修改的用户，则不进行操作
                return ;
            }
        }
        appUserReference =
                this.appUserReferenceService.getOneByUserIdentifier(appUser.getId(), appUser.getIdentifier());
        if (appUserReference == null) {
            throw new UsernameNotFoundException(this.messages.getMessage("UserDetailsManager.nonExist",
                    "user not exist"));
        }
        AppUserReference.AppUserReferenceBuilder builder = AppUserReference.builder(userId)
                .withUsername(appUser.getUsername())
                .withAdditionalKey(appUser.getAdditionalKey())
                .expiredAt(appUser.getUsernameExpiredTime());
        // 如果已经别校验
        if (appUser.getValidated() == Judge.YES) {
            builder.validate(true);
        }
        this.appUserReferenceService.updateById(builder.build());
    }

    /**
     * app不允许删除用户， 如果用户名为空，则不进行操作，如果有值则会抛出异常
     * @param username 用户名
     */
    @Override
    public void deleteUser(String username) {
        if (logger.isDebugEnabled()) {
            logger.debug("App does not have permission to delete user operation");
        }
        Assert.isTrue(!StringUtils.hasText(username), "App does not have permission to delete user operation");
    }

    /**
     * 修改当前用户的密码
     * @param oldPassword current password (for re-authentication if required)
     * @param newPassword the password to change to
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        String userId = (String) SecurityHolder.getAuthentication().getPrincipal();

    }

    @Override
    public boolean userExists(String username) {
        return this.appUserReferenceService.getOneByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return super.loadUserByUsername(username);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(messages, "A message source could not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
        super.setMessageSource(messageSource);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AppUserReferenceService getAppUserReferenceService() {
        return appUserReferenceService;
    }

    public void setAppUserReferenceService(AppUserReferenceService appUserReferenceService) {
        this.appUserReferenceService = appUserReferenceService;
    }

    public AppUserSecretService getAppUserSecretService() {
        return appUserSecretService;
    }

    public void setAppUserSecretService(AppUserSecretService appUserSecretService) {
        this.appUserSecretService = appUserSecretService;
    }
}
