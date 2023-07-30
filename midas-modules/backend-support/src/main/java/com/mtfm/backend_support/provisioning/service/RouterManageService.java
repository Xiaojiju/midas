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
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.provisioning.GroupAuthorityManager;
import com.mtfm.backend_support.provisioning.RouterManager;
import com.mtfm.backend_support.provisioning.authority.GrantedPermission;
import com.mtfm.backend_support.provisioning.authority.GrantedRouter;
import com.mtfm.backend_support.provisioning.mapper.RouterMapper;
import com.mtfm.backend_support.provisioning.mapper.UserManageMapper;
import com.mtfm.core.util.NodeTree;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户权限
 */
public class RouterManageService implements RouterManager {

    private final RouterMapper routerMapper;

    private final UserManageMapper userManageMapper;

    private GroupAuthorityManager groupAuthorityManager;

    public RouterManageService(RouterMapper routerMapper, UserManageMapper userManageMapper, GroupAuthorityManager groupAuthorityManager) {
        this.routerMapper = routerMapper;
        this.userManageMapper = userManageMapper;
        this.groupAuthorityManager = groupAuthorityManager;
    }

    @Override
    public List<GrantedAuthority> loadPermissions(String username) {
        List<GrantedRouter> routers = loadRouters(username);
        if (CollectionUtils.isEmpty(routers)) {
            return new ArrayList<>();
        }
        return routers.stream().map(item -> new GrantedPermission(item.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public NodeTree<GrantedRouter> getRouterTree() {
        List<GrantedRouter> menus = this.routerMapper.selectRouters(null);
        menus.add(GrantedRouter.buildParentNode());
        return NodeTree.build(menus);
    }

    @Override
    public NodeTree<GrantedRouter> getRouterTree(String username) {
        List<GrantedRouter> routers = loadRouters(username);
        routers.add(GrantedRouter.buildParentNode());
        return NodeTree.build(routers);
    }

    @Override
    public NodeTree<GrantedRouter> getRoutersBYGroup(String group) {
        List<GrantedRouter> routers = this.routerMapper.selectRouters(group);
        routers.add(GrantedRouter.buildParentNode());
        return NodeTree.build(routers);
    }

    private List<GrantedRouter> loadRouters(String username) {
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(username, BackendSupportIdentifier.DEFAULT);
        List<GrantedRouter> routers;
        if (solarUser.isAdmin()) {
            routers = this.routerMapper.selectRoutersByUser(null);
        } else {
            routers = this.routerMapper.selectRoutersByUser(solarUser.getId());
        }
        return routers;
    }

    protected RouterMapper getRouterMapper() {
        return routerMapper;
    }

    protected UserManageMapper getUserManageMapper() {
        return userManageMapper;
    }

    protected GroupAuthorityManager getGroupAuthorityManager() {
        return groupAuthorityManager;
    }

    public void setGroupAuthorityManager(GroupAuthorityManager groupAuthorityManager) {
        this.groupAuthorityManager = groupAuthorityManager;
    }
}
