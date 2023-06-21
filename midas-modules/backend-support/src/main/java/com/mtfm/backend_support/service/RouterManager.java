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
package com.mtfm.backend_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.backend_support.entity.SolarMenu;
import com.mtfm.backend_support.service.provisioning.GrantedMenu;
import com.mtfm.backend_support.service.provisioning.GrantedMenuOnRole;
import com.mtfm.core.util.NodeTree;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 路由控制
 */
public interface RouterManager extends IService<SolarMenu> {

    /**
     * 获取所有菜单
     * @return 菜单树
     */
    NodeTree<GrantedMenu> getRouterTree();

    /**
     * 获取用户的菜单
     * @param userId 用户id
     * @return 菜单树
     */
    NodeTree<GrantedMenu> getRouterTree(String userId);

    /**
     * 获取某个角色的菜单
     * @param roleId 角色id
     * @return 菜单树
     */
    NodeTree<GrantedMenu> getRouterTreeByRoleId(String roleId);

    /**
     * 授予角色菜单权限
     * @param grantedMenuOnRole 授予的菜单集合
     * @return true为成功，反之则失败
     */
    boolean grantMenus(GrantedMenuOnRole grantedMenuOnRole);
}
