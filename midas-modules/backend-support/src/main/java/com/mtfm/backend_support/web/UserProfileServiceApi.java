package com.mtfm.backend_support.web;

import com.mtfm.backend_support.provisioning.ManageUserDetailsService;
import com.mtfm.backend_support.provisioning.authority.SimpleUser;
import com.mtfm.backend_support.provisioning.authority.SimpleUserProfile;
import com.mtfm.backend_support.web.params.UserPageQuery;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.Target;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户个人信息Api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class UserProfileServiceApi {

    private final ManageUserDetailsService<SimpleUserProfile> userProfileManager;

    public UserProfileServiceApi(ManageUserDetailsService<SimpleUserProfile> userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    @GetMapping("/profile")
    public RestResult<SimpleUserProfile> getProfile() {
        AppUser appUser = (AppUser) SecurityHolder.getPrincipal();
        return RestResult.success(this.userProfileManager.loadUserDetails(appUser.getUsername()));
    }

    @GetMapping("/{username}/details")
    public RestResult<SimpleUserProfile> getProfile(@PathVariable("username") String username) {
        return RestResult.success(this.userProfileManager.loadUserDetails(username));
    }

    @GetMapping("/users")
    public RestResult<PageTemplate<SimpleUser>> getUsers(UserPageQuery query) {
        return RestResult.success(this.userProfileManager.loadUsers(query));
    }

    @PostMapping("/user")
    public RestResult<Void> updateUser(@RequestBody SimpleUserProfile profile) {
        this.userProfileManager.createOrUpdate(profile);
        return RestResult.success();
    }

    @DeleteMapping("/user")
    public RestResult<Void> deleteUser(@RequestBody Target<String> target) {
        this.userProfileManager.removeUser(target.getTarget());
        return RestResult.success();
    }

    protected ManageUserDetailsService<SimpleUserProfile> getUserProfileManager() {
        return userProfileManager;
    }
}
