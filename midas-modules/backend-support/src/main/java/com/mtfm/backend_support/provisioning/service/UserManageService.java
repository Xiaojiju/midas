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
package com.mtfm.backend_support.provisioning.service;

import com.mtfm.backend_support.BackendSupportIdentifier;
import com.mtfm.backend_support.config.BackendSupportMessageSource;
import com.mtfm.backend_support.entity.SolarSecret;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.entity.SolarUsername;
import com.mtfm.backend_support.provisioning.GroupAuthorityManager;
import com.mtfm.backend_support.provisioning.mapper.UserManageMapper;
import com.mtfm.backend_support.provisioning.mapper.UserSecretMapper;
import com.mtfm.backend_support.provisioning.mapper.UsernameMapper;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.AppUser;
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
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户关联信息管理业务
 */
@Transactional(rollbackFor = Exception.class)
public class UserManageService implements UserDetailsManager, InitializingBean, MessageSourceAware {

    private static final String DEFAULT_PASSWORD = "midas888888";

    private static final Logger logger = LoggerFactory.getLogger(UserManageService.class);

    private final UsernameMapper usernameMapper;

    private final UserSecretMapper userSecretMapper;

    private final GroupAuthorityManager groupAuthorityManager;
    
    private final UserManageMapper userManageMapper;

    private UserDetailsService userDetailsService;
    
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private MessageSourceAccessor messageSource = BackendSupportMessageSource.getAccessor();

    public UserManageService(UsernameMapper usernameMapper, UserSecretMapper userSecretMapper,
                             GroupAuthorityManager groupAuthorityManager, UserManageMapper userManageMapper,
                             UserDetailsService userDetailsService) {
        this.usernameMapper = usernameMapper;
        this.userSecretMapper = userSecretMapper;
        this.groupAuthorityManager = groupAuthorityManager;
        this.userManageMapper = userManageMapper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(AppUser.class, user, "parameter class must extends AppUser.class");
        AppUser appUser = ( AppUser ) user;
        if (appUser.isAdmin()) {
            throw new ServiceException(this.messageSource.getMessage("UserInformation.admin"),
                    SecurityCode.NO_AUTHORITIES.getCode());
        }

        // 用户名
        String username = appUser.getUsername();
        LocalDateTime expiredTime = appUser.getAccountExpiredTime();
        SolarUser solarUser = createUser(username, expiredTime);
        SolarUsername.UserReferenceBuilder userReferenceBuilder = SolarUsername.withUId(solarUser.getId());
        SolarUsername userReference = userReferenceBuilder.withReferenceKey(username)
                .allowable(Judge.YES)
                .identifyBy(BackendSupportIdentifier.DEFAULT)
                .build();
        this.usernameMapper.insert(userReference);

        // 用户密钥
        SolarSecret secret = SolarSecret.builder(solarUser.getId())
                .makeItSecret(StringUtils.hasText(appUser.getPassword()) ? appUser.getPassword() : DEFAULT_PASSWORD,
                        null, passwordEncoder)
                .build();
        this.userSecretMapper.insert(secret);

        // 权限组
        List<GrantedAuthority> authorities = appUser.getAuthorities();
        if (authorities != null) {
            this.groupAuthorityManager.adduserToGroups(appUser.getUsername(), authorities);
        }
    }

    /**
     * 修改用户信息，该方法主要偏向于具有修改用户权限的用户进行修改其他用户的信息，但是不能用于修改认证方式的校验情况
     * {@link SolarUsername#getValidated()}以及{@link SolarUsername#getIdentifier()}，因为identifier在
     * 创建用户的时候就初始化为DEFAULT。其也作为用户默认用户名，其他的认证方式都为副认证方式；
     * {@link AppUser#getAuthorities()}该方式还可以进行修改用户关联角色
     * @param user 此参数{@link AppUser}必须是继承或其本身
     */
    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(AppUser.class, user,"parameter class must extends AppUser.class");
        AppUser appUser = (AppUser) user;
        AppUser targetUser = (AppUser) this.loadUserByUsername(appUser.getUsername());
        if (targetUser.isAdmin()) {
            throw new ServiceException(this.messageSource.getMessage("UserInformation.admin"),
                    SecurityCode.NO_AUTHORITIES.getCode());
        }
        
        // 默认请求方式为DEFAULT
        String identifier = appUser.getIdentifier();
        if (!StringUtils.hasText(identifier)) {
            identifier = BackendSupportIdentifier.DEFAULT;
        }

        // 修改用户状态
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(appUser.getUsername(), BackendSupportIdentifier.DEFAULT);
        if (solarUser == null) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        solarUser.setLocked(appUser.getAccountLocked());
        solarUser.setExpiredTime(appUser.getAccountExpiredTime());
        this.userManageMapper.updateById(solarUser);

        // 修改对应的用户名
        SolarUsername solarUsername = this.usernameMapper.selectUsername(appUser.getUsername(), identifier);
        if (solarUsername != null) {
            if (!solarUsername.getuId().equals(appUser.getId())) {
                throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.hadExist"),
                        SecurityCode.USERNAME_EXIST.getCode());
            }
        } else {
            solarUsername = this.usernameMapper.selectUsername(targetUser.getUsername(), identifier);
            solarUsername.setUsername(appUser.getUsername());
            this.usernameMapper.updateById(solarUsername);
        }

        // 修改用户密码
        if (StringUtils.hasText(appUser.getPassword())) {
            SolarSecret secret = this.userSecretMapper.selectSecretByUserId(appUser.getId());
            secret = SolarSecret.builder(secret)
                    .makeItSecret(appUser.getPassword(), null, passwordEncoder)
                    .build();
            this.userSecretMapper.updateById(secret);
        }
        
        // 修改权限
        List<GrantedAuthority> authorities = appUser.getAuthorities();
        if (authorities != null) {
            this.groupAuthorityManager.adduserToGroups(appUser.getUsername(), authorities);
        }
    }

    @Override
    public void deleteUser(String username) {
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(username, BackendSupportIdentifier.DEFAULT);
        if (solarUser == null) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        if (solarUser.isAdmin()) {
            throw new ServiceException(this.messageSource.getMessage("UserInformation.admin"),
                    SecurityCode.NO_AUTHORITIES.getCode());
        }
        this.userManageMapper.deleteById(solarUser.getId());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String self = (String) authentication.getPrincipal();
        // 优先校验旧密码是否正确
        SolarSecret solarSecret = this.userSecretMapper.selectSecretByUserId(self);
        if (solarSecret == null) {
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.nonExist"),
                    SecurityCode.USER_NOT_FOUND.getCode());
        }
        if (passwordEncoder.matches(oldPassword, solarSecret.getSecret())) {
            solarSecret.setSecret(passwordEncoder.encode(newPassword));
            this.userSecretMapper.updateById(solarSecret);
            return ;
        }
        throw new ServiceException(this.messageSource.getMessage("ReturnResponseAuthenticationFailHandler.bad_credential"),
                SecurityCode.BAD_CREDENTIAL.getCode());
    }

    @Override
    public boolean userExists(String username) {
        SolarUsername userReference = this.usernameMapper.selectUsername(username, null);
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
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(username, BackendSupportIdentifier.DEFAULT);
        if (solarUser != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("username key {} had exist", username);
            }
            throw new ServiceException(this.messageSource.getMessage("UserDetailsManager.hadExist"),
                    SecurityCode.USERNAME_EXIST.getCode());
        }
        solarUser = SolarUser.expiredUser(expiredTime);
        this.userManageMapper.insert(solarUser);
        return solarUser;
    }

    protected UsernameMapper getUsernameMapper() {
        return usernameMapper;
    }

    protected UserSecretMapper getUserSecretMapper() {
        return userSecretMapper;
    }

    protected GroupAuthorityManager getGroupAuthorityManager() {
        return groupAuthorityManager;
    }

    protected UserManageMapper getUserManageMapper() {
        return userManageMapper;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    protected MessageSourceAccessor getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSourceAccessor messageSource) {
        this.messageSource = messageSource;
    }
}
