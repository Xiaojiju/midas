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
package com.mtfm.backend_support.service.user;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.backend_support.entity.SolarBaseInfo;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.entity.SolarUserReference;
import com.mtfm.backend_support.listener.ClearSessionEvent;
import com.mtfm.backend_support.service.UserBaseInfoManager;
import com.mtfm.backend_support.service.UserManager;
import com.mtfm.backend_support.service.UserReferenceManager;
import com.mtfm.backend_support.service.mapper.UserMapper;
import com.mtfm.backend_support.service.provisioning.UserInformation;
import com.mtfm.backend_support.service.query.ValuePageQuery;
import com.mtfm.core.util.page.PageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户详细信息管理业务
 * 其中包含了管理用户基本信息{@link SolarBaseInfo}以及发布事件等升级能力
 */
@Transactional(rollbackFor = Exception.class)
public class UserInformationManageService extends ServiceImpl<UserMapper, SolarUser>
        implements UserDetailsManager, UserManager, InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(UserInformationManageService.class);

    private ApplicationContext applicationContext;
    private UserManageService userManageService;
    private UserBaseInfoManager userBaseInfoManager;
    private UserReferenceManager userReferenceManager;

    public UserInformationManageService(UserManageService userManageService) {
        this(userManageService, new UserBaseInfoManagerService());
    }

    public UserInformationManageService(UserManageService userManageService,
                                        UserBaseInfoManager userBaseInfoManager) {
        this.userManageService = userManageService;
        this.userBaseInfoManager = userBaseInfoManager;
    }

    @Override
    public UserInformation getInformation(String userId) {
        return this.baseMapper.selectInformation(userId);
    }

    @Override
    public PageTemplate<UserInformation> pageList(ValuePageQuery query) {
        IPage<UserInformation> page = new Page<UserInformation>().setCurrent(query.getCurrent())
                .setSize(query.getSize());
        page = this.baseMapper.selectUsers(page, query.getValue());
        return new PageTemplate.PageTemplateBuilder<UserInformation>().setCurrent(page.getCurrent())
                .setSize(page.getSize())
                .setItems(page.getRecords())
                .setTotal(page.getTotal())
                .build();
    }

    @Override
    public void createUser(UserDetails user) {
        Assert.isInstanceOf(UserInformation.class, user, "parameter class must extends UserInformation.class");
        UserInformation userInformation = (UserInformation) user;
        this.userManageService.createUser(userInformation);
        String username = userInformation.getUsername();
        SolarUserReference userReference = this.userReferenceManager.getByReferenceKey(username, null);
        SolarBaseInfo baseInfo = userInformation.unCreatedBaseInfo(userReference.getuId());
        this.userBaseInfoManager.save(baseInfo);
    }

    @Override
    public void updateUser(UserDetails user) {
        Assert.isInstanceOf(UserInformation.class, user, "parameter class must extends UserInformation.class");
        UserInformation userInformation = (UserInformation) user;
        this.userManageService.updateUser(userInformation);
        SolarBaseInfo baseInfo = this.userBaseInfoManager.getByUserId(userInformation.getId());
        if (baseInfo != null) {
            baseInfo = userInformation.createdBaseInfo(baseInfo.getId(), userInformation.getId());
        } else {
            baseInfo = userInformation.unCreatedBaseInfo(userInformation.getId());
        }
        this.userBaseInfoManager.saveOrUpdate(baseInfo);
        clearSession(userInformation.getId());
    }

    @Override
    public void deleteUser(String username) {
        this.userManageService.deleteUser(username);
        // 不删除用户基本信息
//        this.userBaseInfoManager.removeById(username);
        clearSession(username);
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

    @Override
    public void afterPropertiesSet() throws Exception {
        UserReferenceManager userReferenceManager = this.userManageService.getUserReferenceManager();
        Assert.notNull(userReferenceManager, "UserManageService.class missing argument userReferenceManager.class");
        this.userReferenceManager = userReferenceManager;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void clearSession(String userId) {
        if (this.applicationContext != null) {
            this.applicationContext.publishEvent(new ClearSessionEvent(this, userId));
            return ;
        }
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

    public UserBaseInfoManager getUserBaseInfoManager() {
        return userBaseInfoManager;
    }

    public void setUserBaseInfoManager(UserBaseInfoManager userBaseInfoManager) {
        this.userBaseInfoManager = userBaseInfoManager;
    }

    public UserReferenceManager getUserReferenceManager() {
        return userReferenceManager;
    }

    public void setUserReferenceManager(UserReferenceManager userReferenceManager) {
        this.userReferenceManager = userReferenceManager;
    }
}
