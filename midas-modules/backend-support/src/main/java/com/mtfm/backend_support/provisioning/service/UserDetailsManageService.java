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

import com.mtfm.backend_support.config.BackendSupportMessageSource;
import com.mtfm.backend_support.provisioning.mapper.UserManageMapper;
import com.mtfm.security.AppUser;
import com.mtfm.security.provisioning.NullPermissionGrantedManagerImpl;
import com.mtfm.security.provisioning.PermissionGrantedManager;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * 实现用户详情查询，默认开启权限查询
 * @author 一块小饼干
 * @since 1.0.0
 */
public class UserDetailsManageService implements UserDetailsService, MessageSourceAware {

    private final PermissionGrantedManager permissionGrantedManager;

    private final UserManageMapper userManageMapper;

    private boolean enablePermissions;

    private MessageSourceAccessor messages = BackendSupportMessageSource.getAccessor();

    /**
     * 默认开启权限，但是需要重写GrantAuthority,并且进行注入，否则会调用默认{@link NullPermissionGrantedManagerImpl}
     */
    public UserDetailsManageService(PermissionGrantedManager permissionGrantedManager, UserManageMapper userManageMapper) {
        this(permissionGrantedManager, userManageMapper, true);
    }

    public UserDetailsManageService(PermissionGrantedManager permissionGrantedManager, UserManageMapper userManageMapper, boolean enablePermissions) {
        this.permissionGrantedManager = permissionGrantedManager;
        this.userManageMapper = userManageMapper;
        this.enablePermissions = enablePermissions;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.userManageMapper.selectCurrentUserDetailsByUsername(username);
        if (appUser != null) {
            if (this.enablePermissions) {
                List<GrantedAuthority> permissions = loadAuthorities(appUser.getUsername());
                appUser.setAuthorities(permissions);
            }
            return appUser;
        }
        throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.noAuthority",
                new Object[] { username }, "User {0} has no GrantedAuthority"));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected List<GrantedAuthority> loadAuthorities(String username) {
        return permissionGrantedManager.loadPermissions(username);
    }

    protected boolean isEnablePermissions() {
        return enablePermissions;
    }

    public void setEnablePermissions(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }

    protected PermissionGrantedManager getPermissionGrantedManager() {
        return permissionGrantedManager;
    }

    protected UserManageMapper getUserManageMapper() {
        return userManageMapper;
    }

    protected MessageSourceAccessor getMessages() {
        return messages;
    }

    public void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }
}
