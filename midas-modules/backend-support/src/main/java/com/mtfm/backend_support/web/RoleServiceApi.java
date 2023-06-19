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

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/solar/api/v1")
public class RoleServiceApi implements MessageSourceAware {

    private RoleManager roleService;
    private MessageSourceAccessor messageSource;

    public RoleServiceApi(RoleManager roleService) {
        this.roleService = roleService;
        this.messageSource = BackendSupportMessageSource.getAccessor();
    }

    @PostMapping("/role")
    public RestResult<Object> createRole(@RequestBody SolarRole role) throws IOException {
        role.clear();
        if (roleService.saveRole(role)) {
            return RestResult.success();
        }
        return RestResult.fail(messageSource.getMessage("Service.saveFailed"));
    }

    @PutMapping("/role")
    public RestResult<Object> updateRole(@RequestBody SolarRole role) {
        role.clear();
        if (roleService.updateRole(role)) {
            return RestResult.success();
        }
        return RestResult.fail(messageSource.getMessage("Service.putFailed"));
    }

    @GetMapping("/role")
    public RestResult<SolarRole> one(Target<String> target) {
        SolarRole role = roleService.getById(target.getTarget());
        if (Objects.nonNull(role)) {
            return RestResult.success(role);
        }
        return RestResult.fail(messageSource.getMessage("RoleService.nonExist"));
    }

    @DeleteMapping("/role")
    public RestResult<Object> removeRoles(@RequestBody Target<String> target) {
        if (roleService.removeRole(target.getTarget())) {
            return RestResult.success();
        }
        return RestResult.fail(messageSource.getMessage("RoleService.nonExist"));
    }

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
