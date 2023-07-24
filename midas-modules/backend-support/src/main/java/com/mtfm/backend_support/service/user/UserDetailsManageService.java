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
import com.mtfm.security.AppUser;
import com.mtfm.security.service.GrantAuthorityService;
import com.mtfm.security.service.NullGrantAuthorityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserDetailsManageService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsManageService.class);

    private GrantAuthorityService grantAuthorityService;

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
        this.enablePermissions = enablePermissions;
        this.backendUserServiceMapper = backendUserServiceMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.backendUserServiceMapper.selectUserByReferenceKey(username);
        if (appUser != null) {
            if (this.enablePermissions) {
                List<GrantedAuthority> permissions = loadAuthorities(appUser.getId());
                appUser.setAuthorities(permissions);
            }
        }
        return appUser;
    }

    protected List<GrantedAuthority> loadAuthorities(String username) {
        return grantAuthorityService.loadPermissions(username);
    }

    protected GrantAuthorityService getGrantAuthorityService() {
        return this.grantAuthorityService;
    }

    public void setGrantAuthorityService(GrantAuthorityService grantAuthorityService) {
        this.grantAuthorityService = grantAuthorityService;
    }

    protected BackendUserServiceMapper getBackendUserServiceMapper() {
        return backendUserServiceMapper;
    }

    public void setBackendUserServiceMapper(BackendUserServiceMapper backendUserServiceMapper) {
        this.backendUserServiceMapper = backendUserServiceMapper;
    }

    protected boolean isEnablePermissions() {
        return enablePermissions;
    }

    public void setEnablePermissions(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }

}
