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

import com.mtfm.backend_support.service.mapper.BackendUserServiceMapper;
import com.mtfm.backend_support.service.provisioning.UserDto;
import com.mtfm.security.UserTemplate;
import com.mtfm.security.exceptions.NoAuthoritiesException;
import com.mtfm.security.service.EmptyPermissionHandler;
import com.mtfm.security.service.GrantAuthorityHandler;
import com.mtfm.security.service.GrantAuthorityService;
import com.mtfm.security.service.NullGrantAuthorityServiceImpl;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现用户详情查询，默认开启权限查询
 * @author 一块小饼干
 * @since 1.0.0
 */
public class UserDetailsManageService implements UserDetailsService, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsManageService.class);
    private GrantAuthorityService grantAuthorityService = new NullGrantAuthorityServiceImpl();
    private MessageSourceAccessor messageSource = SpringSecurityMessageSource.getAccessor();
    private GrantAuthorityHandler permissionHandler;
    private BackendUserServiceMapper backendUserServiceMapper;
    private boolean enablePermissions;

    /**
     * 默认开启权限，但是需要重写GrantAuthority,并且进行注入，否则会调用默认{@link NullGrantAuthorityServiceImpl}
     */
    public UserDetailsManageService(BackendUserServiceMapper backendUserServiceMapper) {
        this(backendUserServiceMapper, true);
    }

    public UserDetailsManageService(BackendUserServiceMapper backendUserServiceMapper,
                                    boolean enablePermissions) {
        this(backendUserServiceMapper, new EmptyPermissionHandler(), enablePermissions);
    }

    public UserDetailsManageService(BackendUserServiceMapper backendUserServiceMapper,
                                    GrantAuthorityHandler permissionHandler,
                                    boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
        this.backendUserServiceMapper = backendUserServiceMapper;
        this.permissionHandler = permissionHandler;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = this.backendUserServiceMapper.selectUserByReferenceKey(username);
        List<GrantedAuthority> permissions = new ArrayList<>();
        if (userDto != null) {
            if (this.enablePermissions) {
                permissions = permissionHandler.handle(loadAuthorities(userDto.getUniqueId()));
            }
        }
        return createUserTemplate(username, userDto, permissions);
    }

    protected List<GrantedAuthority> loadAuthorities(String username) {
        return grantAuthorityService.loadPermissions(username);
    }

    public UserTemplate createUserTemplate(String username, UserDto userDto, List<GrantedAuthority> permissions) {
        if (userDto == null) {
            logger.debug("username {} not found", username);
            throw new UsernameNotFoundException(this.messageSource.getMessage("JdbcDaoImpl.notFound",
                    new Object[] { username }, "Username {0} wrong with username or password"));
        }
        if (this.enablePermissions) {
            if (CollectionUtils.isEmpty(permissions)) {
                logger.debug("Username {} authorities could not be null", username);
                throw new NoAuthoritiesException(this.messageSource.getMessage("JdbcDaoImpl.nonPermissions",
                        new Object[] { username }, "Username {0} has no permission"));
            }
        }
        LocalDateTime now = LocalDateTime.now();
        boolean credentialsExpired = false, methodExpired = false, accountExpired = false;
        LocalDateTime credentialsExpiredTime = userDto.getCredentialsExpiredTime();
        if (credentialsExpiredTime != null) {
            credentialsExpired = credentialsExpiredTime.isBefore(now);
        }
        LocalDateTime methodExpiredTime = userDto.getCurrentMethodExpiredTime();
        if (methodExpiredTime != null) {
            methodExpired = methodExpiredTime.isBefore(now);
        }
        LocalDateTime accountExpiredTime = userDto.getAccountExpiredTime();
        if (accountExpiredTime != null) {
            accountExpired = accountExpiredTime.isBefore(now);
        }
        return new UserTemplate(userDto.getUniqueId(), userDto.getSecretKey(), userDto.getAccountLocked() != Judge.YES,
                !accountExpired, !credentialsExpired, userDto.getAccountLocked() != Judge.YES,
                permissions, userDto.getCurrentMethod(), !methodExpired,
                userDto.getCurrentMethodLocked() !=Judge.YES);
    }

    public MessageSourceAccessor getMessageSource() {
        return this.messageSource;
    }

    public GrantAuthorityService getGrantAuthorityService() {
        return this.grantAuthorityService;
    }

    public void setGrantAuthorityService(GrantAuthorityService grantAuthorityService) {
        this.grantAuthorityService = grantAuthorityService;
    }

    public void setMessageSource(MessageSourceAccessor messageSource) {
        this.messageSource = messageSource;
    }

    public BackendUserServiceMapper getBackendUserServiceMapper() {
        return backendUserServiceMapper;
    }

    public void setBackendUserServiceMapper(BackendUserServiceMapper backendUserServiceMapper) {
        this.backendUserServiceMapper = backendUserServiceMapper;
    }

    public GrantAuthorityHandler getPermissionHandler() {
        return permissionHandler;
    }

    public void setPermissionHandler(GrantAuthorityHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    public boolean isEnablePermissions() {
        return enablePermissions;
    }

    public void setEnablePermissions(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }

}
