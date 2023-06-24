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

import com.mtfm.backend_support.service.RouterManager;
import com.mtfm.backend_support.service.provisioning.GrantedMenu;
import com.mtfm.backend_support.service.provisioning.GrantedMenuOnRole;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.Target;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 菜单Api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class MenuServiceApi {

    private RouterManager routerManager;

    public MenuServiceApi(RouterManager routerManager) {
        this.routerManager = routerManager;
    }

    /**
     * 获取自己的菜单
     * @return 菜单树
     */
    @GetMapping("/self/menus")
    public RestResult<List<GrantedMenu>> getMenus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return RestResult.success(routerManager.getRouterTree((String) authentication.getPrincipal()).getElement().getNodes());
    }

    /**
     * 获取全部菜单或某个角色的菜单
     * @param role 角色id (not required)
     * @return 菜单树
     */
    @GetMapping("/menus")
    public RestResult<List<GrantedMenu>> getAll(@RequestParam(required = false) Target<String> role) {
        if (role == null || !StringUtils.hasText(role.getTarget())) {
            return RestResult.success(routerManager.getRouterTree().getElement().getNodes());
        } else {
            return RestResult.success(routerManager.getRouterTreeByRoleId(role.getTarget()).getElement().getNodes());
        }
    }

    /**
     * 给我角色授予权限
     */
    @PostMapping("/menus/granted")
    public RestResult<Object> grantRouter(@RequestBody GrantedMenuOnRole grantedMenuOnRole) {
        routerManager.grantMenus(grantedMenuOnRole);
        return RestResult.success();
    }

}
