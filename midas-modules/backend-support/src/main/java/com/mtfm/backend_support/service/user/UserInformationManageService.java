package com.mtfm.backend_support.service.user;


import com.mtfm.backend_support.listener.ClearSessionEvent;
import com.mtfm.backend_support.service.mapper.UserBaseInfoMapper;
import com.mtfm.backend_support.service.provisioning.UserInformation;
import com.mtfm.backend_support.service.query.ValuePageQuery;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.security.UserTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

public class UserInformationManageService implements UserDetailsManager, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(UserInformationManageService.class);

    private UserManageService userManageService;
    private UserBaseInfoMapper userBaseInfoMapper;
    private ApplicationContext applicationContext;

    public UserInformationManageService(UserManageService userManageService, UserBaseInfoMapper userBaseInfoMapper) {
        this(userManageService, userBaseInfoMapper, null);
    }

    public UserInformationManageService(UserManageService userManageService,
                                        UserBaseInfoMapper userBaseInfoMapper,
                                        ApplicationContext applicationContext) {
        this.userManageService = userManageService;
        this.userBaseInfoMapper = userBaseInfoMapper;
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void createUser(UserDetails user) {
        this.userManageService.createUser(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        this.userManageService.updateUser(user);
        if (this.applicationContext != null) {
            UserTemplate userDetails = (UserTemplate) this.loadUserByUsername(user.getUsername());
            this.applicationContext.publishEvent(new ClearSessionEvent(this, userDetails.getUsername()));
            return ;
        }
        after();
    }

    @Override
    public void deleteUser(String username) {
        this.userManageService.deleteUser(username);
        if (this.applicationContext != null) {
            this.applicationContext.publishEvent(new ClearSessionEvent(this, username));
            return ;
        }
        after();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        this.userManageService.changePassword(oldPassword, newPassword);
    }

    @Override
    public boolean userExists(String username) {
        return this.userManageService.userExists(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userManageService.loadUserByUsername(username);
    }

    public UserInformation loadUser(String userId) {
        return null;
    }

    public PageTemplate<UserInformation> loadUsers(ValuePageQuery pageQuery) {
        return null;
    }

    private void after() {
        if (logger.isDebugEnabled()) {
            logger.debug("application context is null, could publish event from {}", UserInformationManageService.class);
        }
    }

    public UserManageService getUserManageService() {
        return userManageService;
    }

    public void setUserManageService(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    public UserBaseInfoMapper getUserBaseInfoMapper() {
        return userBaseInfoMapper;
    }

    public void setUserBaseInfoMapper(UserBaseInfoMapper userBaseInfoMapper) {
        this.userBaseInfoMapper = userBaseInfoMapper;
    }

}
