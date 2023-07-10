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

import com.mtfm.backend_support.entity.SolarRole;
import com.mtfm.backend_support.service.UserRoleManager;
import com.mtfm.backend_support.service.mapper.UserRoleMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户角色管理
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleManageService implements UserRoleManager, MessageSourceAware, InitializingBean {

    protected MessageSourceAccessor messageSource;

    private final UserRoleMapper userRoleMapper;

    public UserRoleManageService(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<SolarRole> loadRoles(String userId) {
        return this.userRoleMapper.selectRoleIds(userId);
    }

    @Override
    public boolean modifyRoles(String userId, Collection<? extends GrantedAuthority> roles) {
        this.userRoleMapper.deleteRoles(userId, null);
        if (roles != null && !CollectionUtils.isEmpty(roles)) {
            List<String> roleIds = roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            return this.userRoleMapper.insertRoles(userId, roleIds) > 0;
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messageSource, "message source could not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

}
