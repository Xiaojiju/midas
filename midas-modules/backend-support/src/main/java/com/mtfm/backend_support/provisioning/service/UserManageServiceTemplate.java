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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtfm.backend_support.BackendSupportIdentifier;
import com.mtfm.backend_support.config.BackendSupportMessageSource;
import com.mtfm.backend_support.entity.SolarUserProfile;
import com.mtfm.backend_support.entity.SolarUsername;
import com.mtfm.backend_support.provisioning.ManageUserDetailsService;
import com.mtfm.backend_support.provisioning.authority.SimpleUser;
import com.mtfm.backend_support.provisioning.authority.SimpleUserProfile;
import com.mtfm.backend_support.provisioning.mapper.UserManageMapper;
import com.mtfm.backend_support.provisioning.mapper.UserProfileMapper;
import com.mtfm.backend_support.provisioning.mapper.UsernameMapper;
import com.mtfm.backend_support.web.params.UserPageQuery;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.core.util.page.PageQuery;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityCode;
import com.mtfm.tools.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.provisioning.UserDetailsManager;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户管理模板方法
 */
public class UserManageServiceTemplate implements ManageUserDetailsService<SimpleUserProfile>, MessageSourceAware {

    private MessageSourceAccessor messages = BackendSupportMessageSource.getAccessor();

    private UserDetailsManager userDetailsManager;

    private final UserProfileMapper userProfileMapper;

    private final UsernameMapper usernameMapper;

    private final UserManageMapper userManageMapper;

    public UserManageServiceTemplate(UserDetailsManager userDetailsManager, UserProfileMapper userProfileMapper,
                                     UsernameMapper usernameMapper, UserManageMapper userManageMapper) {
        this.userDetailsManager = userDetailsManager;
        this.userProfileMapper = userProfileMapper;
        this.usernameMapper = usernameMapper;
        this.userManageMapper = userManageMapper;
    }

    @Override
    public void createOrUpdate(SimpleUserProfile userDetails) {
        String username = obtainUsername(userDetails);
        AppUser appUser = transferTo(userDetails);
        SolarUserProfile userProfile = SolarUserProfile.builder(null)
                .withAvatar(userDetails.getAvatarUrl())
                .withGender(userDetails.getGender())
                .withNickname(userDetails.getNickname())
                .build();
        SolarUsername solarUsername = this.usernameMapper.selectUsername(username, BackendSupportIdentifier.DEFAULT);
        if (solarUsername != null) {
            if (AppUser.isAdmin(solarUsername.getuId())) {
                throw new ServiceException(this.messages.getMessage("UserInformation.admin"),
                        SecurityCode.NO_AUTHORITIES.getCode());
            }
            SolarUserProfile solarUserProfile = this.userProfileMapper.selectProfileByUsername(appUser.getUsername());
            userProfile.setuId(solarUserProfile.getId());
            userProfile.setId(solarUserProfile.getId());
            this.userDetailsManager.updateUser(appUser);
            this.userProfileMapper.updateById(userProfile);
        } else {
            this.userDetailsManager.createUser(appUser);
            solarUsername = this.usernameMapper.selectUsername(username, BackendSupportIdentifier.DEFAULT);
            userProfile.setuId(solarUsername.getuId());
            this.userProfileMapper.insert(userProfile);
        }
    }

    @Override
    public SimpleUserProfile loadUserDetails(String username) {
        return this.userManageMapper.selectDetails(username);
    }

    @Override
    public PageTemplate<SimpleUser> loadUsers(PageQuery query) {
        UserPageQuery userPageQuery = (UserPageQuery) query;
        Page<SimpleUser> page = new Page<SimpleUser>().setCurrent(query.getCurrent()).setSize(query.getSize());
        page = this.userManageMapper.selectUserPage(page, userPageQuery.getUsername(), userPageQuery.getNickname());
        return new PageTemplate<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    @Override
    public void removeUser(String username) {
        this.userDetailsManager.deleteUser(username);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    private String obtainUsername(SimpleUserProfile details) {
        String username = details.getUsername();
        if (!StringUtils.hasText(username)) {
            throw new ServiceException(this.messages.getMessage("UserManageServiceTemplate.notFoundUserName",
                    "could not found user by empty username text"), SecurityCode.USER_NOT_FOUND.getCode());
        }
        return username;
    }

    private AppUser transferTo(SimpleUserProfile userDetails) {
        return AppUser.builder(userDetails.getUsername(), BackendSupportIdentifier.DEFAULT)
                .accountExpiredAt(userDetails.getExpiredTime())
                .putAuthorities(userDetails.getGroups())
                .usedUsername(true)
                .withPassword(userDetails.getPassword())
                .build();
    }

    protected MessageSourceAccessor getMessages() {
        return messages;
    }

    public void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }

    protected UserDetailsManager getUserDetailsManager() {
        return userDetailsManager;
    }

    public void setUserDetailsManager(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    protected UserProfileMapper getUserProfileMapper() {
        return userProfileMapper;
    }

    protected UsernameMapper getUsernameMapper() {
        return usernameMapper;
    }

    protected UserManageMapper getUserMapper() {
        return userManageMapper;
    }
}
