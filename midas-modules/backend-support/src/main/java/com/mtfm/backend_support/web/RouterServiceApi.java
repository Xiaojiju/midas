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
package com.mtfm.backend_support.web;

import com.mtfm.backend_support.provisioning.GroupAuthorityManager;
import com.mtfm.backend_support.provisioning.RouterManager;
import com.mtfm.backend_support.provisioning.authority.GrantGroupRouter;
import com.mtfm.backend_support.provisioning.authority.GrantedRouter;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 菜单Api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class RouterServiceApi {

    private final RouterManager routerManager;

    private final GroupAuthorityManager groupAuthorityManager;

    public RouterServiceApi(RouterManager routerManager, GroupAuthorityManager groupAuthorityManager) {
        this.routerManager = routerManager;
        this.groupAuthorityManager = groupAuthorityManager;
    }

    /**
     * 获取自己的菜单
     * @return 菜单树
     */
    @GetMapping("/self/routers")
    public RestResult<List<GrantedRouter>> getMenus() {
        AppUser appUser = (AppUser) SecurityHolder.getPrincipal();
        return RestResult.success(routerManager.getRouterTree(appUser.getUsername()).getElement().getNodes());
    }

    /**
     * 某个权限组的菜单
     * @param group 权限组名称
     * @return 菜单树
     */
    @GetMapping("/{group}/routers")
    public RestResult<List<GrantedRouter>> getAll(@PathVariable("group") String group) {
        return RestResult.success(routerManager.getRoutersBYGroup(group).getElement().getNodes());
    }

    /**
     * 全部权限组的菜单
     * @return 菜单树
     */
    @GetMapping("/routers")
    public RestResult<List<GrantedRouter>> getAll() {
        return RestResult.success(routerManager.getRouterTree().getElement().getNodes());
    }

    /**
     * 全部权限组的菜单
     * @return 菜单树
     */
    @GetMapping("/{username}/routers")
    public RestResult<List<GrantedRouter>> getUserAll(@PathVariable("username") String username) {
        return RestResult.success(routerManager.getRouterTree(username).getElement().getNodes());
    }

    /**
     * 给我角色授予权限
     */
    @PostMapping("/routers/granted")
    public RestResult<Object> grantRouter(@RequestBody GrantGroupRouter grantGroupRouter) {
        groupAuthorityManager.addGroupAuthorities(grantGroupRouter.getGroupName(), grantGroupRouter.getRouters());
        return RestResult.success();
    }

    public RouterManager getRouterManager() {
        return routerManager;
    }
}
