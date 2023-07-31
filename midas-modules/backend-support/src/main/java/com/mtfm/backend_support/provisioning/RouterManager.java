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
package com.mtfm.backend_support.provisioning;

import com.mtfm.backend_support.provisioning.authority.GrantedRouter;
import com.mtfm.core.util.NodeTree;
import com.mtfm.security.provisioning.PermissionGrantedManager;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 路由控制
 */
public interface RouterManager extends PermissionGrantedManager {

    /**
     * 获取所有菜单
     * @return 菜单树
     */
    NodeTree<GrantedRouter> getRouterTree();

    /**
     * 获取用户的菜单
     * @param userId 用户id
     * @return 菜单树
     */
    NodeTree<GrantedRouter> getRouterTree(String username);

    /**
     * 获取某个角色的菜单
     * @param roleId 角色id
     * @return 菜单树
     */
    NodeTree<GrantedRouter> getRoutersBYGroup(String group);
}