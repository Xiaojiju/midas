package com.mtfm.backend_support.web;

import com.mtfm.backend_support.service.provisioning.UserInformation;
import com.mtfm.backend_support.service.query.ValuePageQuery;
import com.mtfm.backend_support.service.user.UserInformationManageService;
import com.mtfm.backend_support.service.user.UserManageService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.Target;
import com.mtfm.core.util.page.PageTemplate;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solar/api/v1")
public class UserServiceApi implements MessageSourceAware {

    private UserInformationManageService userManage;
    private MessageSourceAccessor messageSource;

    public UserServiceApi(UserInformationManageService userManage) {
        this.userManage = userManage;
    }

    @PostMapping("/user")
    public RestResult<Object> createUser(@RequestBody UserInformation userInformation) {
        this.userManage.createUser(userInformation);
        return RestResult.success();
    }

    @PutMapping("/user")
    public RestResult<Object> updateUser(@RequestBody UserInformation userInformation) {
        this.userManage.updateUser(userInformation);
        return RestResult.success();
    }

    @DeleteMapping("/user")
    public RestResult<Object> removeUser(@RequestBody Target<String> target) {
        this.userManage.deleteUser(target.getTarget());
        return RestResult.success();
    }

    @GetMapping("/user")
    public RestResult<UserInformation> getUser(Target<String> target) {
        UserInformation userInformation = this.userManage.getInformation(target.getTarget());
        if (userInformation == null) {
            return RestResult.fail(this.messageSource.getMessage("UserDetailsManager.nonExist"));
        }
        return RestResult.success();
    }

    @GetMapping("/users")
    public RestResult<PageTemplate<UserInformation>> getUsers(ValuePageQuery valuePageQuery) {
        return RestResult.success(this.userManage.pageList(valuePageQuery));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    public UserInformationManageService getUserManage() {
        return userManage;
    }

    public void setUserManage(UserInformationManageService userManage) {
        this.userManage = userManage;
    }
}
