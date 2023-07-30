package com.mtfm.backend_support.web;

import com.mtfm.backend_support.provisioning.GroupAuthorityManager;
import com.mtfm.backend_support.provisioning.authority.GrantGroupRouter;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.Target;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 权限组Api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class GroupServiceApi {

    private final GroupAuthorityManager groupAuthorityManager;

    public GroupServiceApi(GroupAuthorityManager groupAuthorityManager) {
        this.groupAuthorityManager = groupAuthorityManager;
    }

    @GetMapping("/{username}/groups")
    public RestResult<GrantGroupRouter> getGroups(@PathVariable("username") String username) {
        List<GrantedAuthority> authorities = this.groupAuthorityManager.findGroupFromUser(username);
        return RestResult.success(new GrantGroupRouter(username, authorities));
    }

    @PostMapping("/group")
    public RestResult<Void> addGroup(@RequestBody GrantGroupRouter grantGroupRouter) {
        this.groupAuthorityManager.createGroup(grantGroupRouter.getGroupName(), grantGroupRouter.getRouters());
        return RestResult.success();
    }

    @PutMapping("/group")
    public RestResult<Void> updateGroup(@RequestBody GrantGroupRouter grantGroupRouter) {
        this.groupAuthorityManager.addGroupAuthorities(grantGroupRouter.getGroupName(), grantGroupRouter.getRouters());
        return RestResult.success();
    }

    @DeleteMapping("/group")
    public RestResult<Void> removeGroup(@RequestBody Target<String> target) {
        this.groupAuthorityManager.deleteGroup(target.getTarget());
        return RestResult.success();
    }

    protected GroupAuthorityManager getGroupAuthorityManager() {
        return groupAuthorityManager;
    }
}
