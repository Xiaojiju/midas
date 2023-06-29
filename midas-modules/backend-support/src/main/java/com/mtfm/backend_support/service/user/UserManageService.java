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
package com.mtfm.backend_support.service.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.backend_support.BackendSupportIdentifier;
import com.mtfm.backend_support.entity.SolarSecret;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.entity.SolarUserReference;
import com.mtfm.backend_support.service.UserReferenceManager;
import com.mtfm.backend_support.service.UserRoleManager;
import com.mtfm.backend_support.service.UserSecretManager;
import com.mtfm.backend_support.service.mapper.UserMapper;
import com.mtfm.backend_support.service.provisioning.UserDetailSample;
import com.mtfm.backend_support.service.provisioning.UserSample;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.SecurityCode;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户关联信息管理业务
 */
@Transactional(rollbackFor = Exception.class)
public class UserManageService extends ServiceImpl<UserMapper, SolarUser> 
        implements UserDetailsManager, InitializingBean, MessageSourceAware {

    private static final String DEFAULT_PASSWORD = "midas888888";
    private static final Logger logger = LoggerFactory.getLogger(UserManageService.class);
    private UserDetailsService userDetailsService;
    private UserReferenceManager userReferenceManager;
    private UserSecretManager userSecretManager;
    private UserRoleManager userRoleManager;
    private MessageSourceAccessor messageSource;

    public UserManageService(UserDetailsService userDetailsService, UserReferenceManager userReferenceManager, 
                             UserSecretManager userSecretManager, UserRoleManager userRoleManager) {
        this.userDetailsService = userDetailsService;
        this.userReferenceManager = userReferenceManager;
        this.userSecretManager = userSecretManager;
        this.userRoleManager = userRoleManager;
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(UserSample.class, user, "parameter class must extends UserSample.class");
        UserSample userSample = ( UserSample ) user;
        String username = userSample.getUsername();
        LocalDateTime expiredTime = userSample.getExpiredTime();
        SolarUser solarUser = createUser(username, expiredTime);
        SolarUserReference.UserReferenceBuilder userReferenceBuilder = SolarUserReference.withUId(solarUser.getId());
        SolarUserReference userReference = userReferenceBuilder.withReferenceKey(username)
                .allowable(Judge.YES)
                .identifyBy(BackendSupportIdentifier.DEFAULT)
                .build();
        this.userReferenceManager.save(userReference);
        SolarSecret secret = SolarSecret.builder(solarUser.getId())
                .makeItSecret(StringUtils.hasText(userSample.getPassword()) ? userSample.getPassword() : DEFAULT_PASSWORD,
                        null, PasswordEncoderFactories.createDelegatingPasswordEncoder())
                .build();
        this.userSecretManager.save(secret);
        this.userRoleManager.modifyRoles(solarUser.getId(), userSample.getAuthorities());
    }

    /**
     * 修改用户信息，该方法主要偏向于具有修改用户权限的用户进行修改其他用户的信息，但是不能用于修改认证方式的校验情况
     * {@link SolarUserReference#getValidated()}以及{@link SolarUserReference#getIdentifier()}，因为identifier在
     * 创建用户的时候就初始化为DEFAULT。其也作为用户默认用户名，其他的认证方式都为副认证方式；
     * {@link UserDetailSample#getAuthorities()}该方式还可以进行修改用户关联角色
     * @param user 此参数{@link UserDetailSample}必须是继承或其本身
     */
    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(UserDetailSample.class, user,"parameter class must extends UserDetailSample.class");
        UserDetailSample sample = (UserDetailSample) user;
        String identifier = sample.getIdentifier();
        if (!StringUtils.hasText(identifier)) {
            identifier = BackendSupportIdentifier.DEFAULT;
        }
        SolarUser solarUser = this.getById(sample.getId());
        if (solarUser == null) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        if (solarUser.isAdmin()) {
            throw new ServiceException(this.messageSource.getMessage("UserInformation.admin"),
                    SecurityCode.NO_AUTHORITIES.getCode());
        }
        solarUser.setLocked(sample.getLocked());
        solarUser.setExpiredTime(sample.getExpiredTime());
        this.updateById(solarUser);
        SolarUserReference userReference = this.userReferenceManager.getByReferenceKey(user.getUsername(), identifier);
        if (userReference != null && !userReference.getuId().equals(sample.getId())) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.hadExist"),
                    SecurityCode.USERNAME_EXIST.getCode());
        }
        userReference = this.userReferenceManager.getByUserId(sample.getId(), identifier);
        userReference = SolarUserReference.builder(userReference)
                .withReferenceKey(sample.getUsername())
                .build();
        this.userReferenceManager.updateById(userReference);
        if (StringUtils.hasText(sample.getPassword())) {
            SolarSecret secret = this.userSecretManager.getOne(sample.getId());
            secret = SolarSecret.builder(secret)
                    .makeItSecret(sample.getPassword(), null, PasswordEncoderFactories.createDelegatingPasswordEncoder())
                    .build();
            this.userSecretManager.updateById(secret);
        }
        Collection<? extends GrantedAuthority> authorities = sample.getAuthorities();
        if (authorities != null) {
            this.userRoleManager.modifyRoles(sample.getId(), sample.getAuthorities());
        }
    }

    @Override
    public void deleteUser(String username) {
        SolarUser solarUser = this.getById(username);
        if (solarUser == null || solarUser.getDeleted() == Judge.YES.getCode()) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        if (solarUser.isAdmin()) {
            throw new ServiceException(this.messageSource.getMessage("UserInformation.admin"),
                    SecurityCode.NO_AUTHORITIES.getCode());
        }
        this.removeById(username);
        this.userRoleManager.modifyRoles(username, null);
        this.userReferenceManager.removeUser(username);
        this.userSecretManager.removeUser(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String self = (String) authentication.getPrincipal();
        // 优先校验旧密码是否正确
        SolarSecret solarSecret = this.userSecretManager.getOne(self);
        if (solarSecret == null) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (passwordEncoder.matches(oldPassword, solarSecret.getSecret())) {
            solarSecret.setSecret(passwordEncoder.encode(newPassword));
            this.userSecretManager.updateById(solarSecret);
            return ;
        }
        throw new ServiceException(this.messageSource.getMessage("ReturnResponseAuthenticationFailHandler.bad_credential"),
                SecurityCode.BAD_CREDENTIAL.getCode());
    }

    @Override
    public boolean userExists(String username) {
        SolarUserReference userReference = this.userReferenceManager.getByReferenceKey(username, null);
        return userReference != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userDetailsService.loadUserByUsername(username);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messageSource, "message source could not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    protected SolarUser createUser(String username, LocalDateTime expiredTime) {
        SolarUserReference userReference = this.userReferenceManager.getByReferenceKey(username, BackendSupportIdentifier.DEFAULT);
        if (userReference != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("user reference key {} had exist", username);
            }
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.hadExist"),
                    SecurityCode.USERNAME_EXIST.getCode());
        }
        SolarUser solarUser = SolarUser.expiredUser(expiredTime);
        this.save(solarUser);
        return solarUser;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserReferenceManager getUserReferenceManager() {
        return userReferenceManager;
    }

    public void setUserReferenceManager(UserReferenceManager userReferenceManager) {
        this.userReferenceManager = userReferenceManager;
    }

    public UserSecretManager getUserSecretManager() {
        return userSecretManager;
    }

    public void setUserSecretManager(UserSecretManager userSecretManager) {
        this.userSecretManager = userSecretManager;
    }

    public UserRoleManager getUserRoleManager() {
        return userRoleManager;
    }

    public void setUserRoleManager(UserRoleManager userRoleManager) {
        this.userRoleManager = userRoleManager;
    }

    public MessageSourceAccessor getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSourceAccessor messageSource) {
        this.messageSource = messageSource;
    }
}
