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
import com.mtfm.backend_support.entity.SolarMenu;
import com.mtfm.backend_support.entity.SolarRole;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.service.RouterManager;
import com.mtfm.backend_support.service.UserRoleManager;
import com.mtfm.backend_support.service.mapper.MenuMapper;
import com.mtfm.backend_support.service.provisioning.GrantedMenu;
import com.mtfm.backend_support.service.provisioning.GrantedMenuOnRole;
import com.mtfm.core.util.NodeTree;
import com.mtfm.security.core.GrantPermission;
import com.mtfm.security.service.GrantAuthorityService;
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
public class GrantUserAuthorityService extends ServiceImpl<MenuMapper, SolarMenu> implements GrantAuthorityService, RouterManager {

    private UserRoleManager userRoleManager;

    public GrantUserAuthorityService(UserRoleManager userRoleManager) {
        this.userRoleManager = userRoleManager;
    }

    @Override
    public List<GrantedAuthority> loadPermissions(String uniqueId) {
        List<GrantedMenu> menus = loadMenus(uniqueId);
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<>();
        }
        return menus.stream().map(item -> new GrantPermission(item.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public NodeTree<GrantedMenu> getRouterTree() {
        List<GrantedMenu> menus = this.baseMapper.selectMenus();
        menus.add(GrantedMenu.buildParentNode());
        return NodeTree.build(menus);
    }

    @Override
    public NodeTree<GrantedMenu> getRouterTree(String userId) {
        List<GrantedMenu> menus = loadMenus(userId);
        menus.add(GrantedMenu.buildParentNode());
        return NodeTree.build(menus);
    }

    @Override
    public NodeTree<GrantedMenu> getRouterTreeByRoleId(String roleId) {
        List<GrantedMenu> menus = this.baseMapper.selectMenusByRoleId(roleId);
        menus.add(GrantedMenu.buildParentNode());
        return NodeTree.build(menus);
    }

    @Override
    public boolean grantMenus(GrantedMenuOnRole grantedMenuOnRole) {
        List<GrantedMenu> grantedMenus = grantedMenuOnRole.getWrapper();
        if (CollectionUtils.isEmpty(grantedMenus)) {
            return false;
        }
        List<String> menuIds = grantedMenus.stream().map(GrantedMenu::getKey).collect(Collectors.toList());
        return this.baseMapper.insertMenusOnRole(grantedMenuOnRole.getRoleId(), menuIds) > 0;
    }

    private List<GrantedMenu> loadMenus(String userId) {
        boolean isAdmin = SolarUser.isAdmin(userId);
        List<GrantedMenu> menus;
        if (isAdmin) {
            menus = this.baseMapper.selectMenus();
        } else {
            List<SolarRole> roles = userRoleManager.loadRoles(userId);
            if (CollectionUtils.isEmpty(roles)) {
                return new ArrayList<>();
            }
            List<String> collect = roles.stream().map(SolarRole::getAuthority).collect(Collectors.toList());
            menus = this.baseMapper.selectMenusByRoleIds(collect);
        }
        return menus;
    }

    protected UserRoleManager getUserRoleManager() {
        return userRoleManager;
    }

    public void setUserRoleManager(UserRoleManager userRoleManager) {
        this.userRoleManager = userRoleManager;
    }
}
