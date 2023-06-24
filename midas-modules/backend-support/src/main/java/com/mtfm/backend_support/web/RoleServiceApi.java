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

import com.mtfm.backend_support.config.BackendSupportMessageSource;
import com.mtfm.backend_support.entity.SolarRole;
import com.mtfm.backend_support.service.RoleManager;
import com.mtfm.backend_support.service.query.ValuePageQuery;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.BatchWrapper;
import com.mtfm.core.util.Target;
import com.mtfm.core.util.page.PageTemplate;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 角色管理api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class RoleServiceApi implements MessageSourceAware {

    private RoleManager roleService;
    private MessageSourceAccessor messageSource;

    public RoleServiceApi(RoleManager roleService) {
        this.roleService = roleService;
        this.messageSource = BackendSupportMessageSource.getAccessor();
    }

    /**
     * 新增角色
     * @param role 角色的基本信息
     * @return message中为OK则为成功，其他则失败
     */
    @PostMapping("/role")
    public RestResult<Object> createRole(@RequestBody SolarRole role) {
        role.clear();
        if (roleService.saveRole(role)) {
            return RestResult.success();
        }
        return RestResult.fail(messageSource.getMessage("Service.saveFailed"));
    }

    /**
     * 修改角色
     * @param role 角色信息
     * @return message中为OK则为成功，其他则失败
     */
    @PutMapping("/role")
    public RestResult<Object> updateRole(@RequestBody SolarRole role) {
        role.clear();
        if (roleService.updateRole(role)) {
            return RestResult.success();
        }
        return RestResult.fail(messageSource.getMessage("Service.putFailed"));
    }

    /**
     * 获取角色详情信息
     * @param target 角色id
     * @return 角色详细信息
     */
    @GetMapping("/role")
    public RestResult<SolarRole> one(Target<String> target) {
        SolarRole role = roleService.getById(target.getTarget());
        if (Objects.nonNull(role)) {
            return RestResult.success(role);
        }
        return RestResult.fail(messageSource.getMessage("RoleService.nonExist"));
    }

    /**
     * 删除角色
     * @param target 角色id
     * @return message中为OK则为成功，其他则失败
     */
    @DeleteMapping("/role")
    public RestResult<Object> removeRole(@RequestBody Target<String> target) {
        if (roleService.removeRole(target.getTarget())) {
            return RestResult.success();
        }
        return RestResult.fail(messageSource.getMessage("RoleService.nonExist"));
    }

    /**
     * 批量删除角色
     * @param wrapper 角色id集合
     * @return message中为OK则为成功，其他则失败
     */
    @DeleteMapping("/roles")
    public RestResult<Object> removeRoles(@RequestBody BatchWrapper<String> wrapper) {
        if (roleService.removeRoles(wrapper.getTargets())) {
            return RestResult.success();
        }
        return RestResult.fail(messageSource.getMessage("RoleService.nonExist"));
    }

    @GetMapping("/roles")
    public RestResult<PageTemplate<SolarRole>> page(ValuePageQuery pageQuery) {
        return RestResult.success(roleService.pageList(pageQuery));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    public RoleManager getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleManager roleService) {
        this.roleService = roleService;
    }
}
