package com.mtfm.backend_support.provisioning.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mtfm.backend_support.BackendSupportIdentifier;
import com.mtfm.backend_support.config.BackendSupportMessageSource;
import com.mtfm.backend_support.entity.SolarGroup;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.provisioning.GroupAuthorityManager;
import com.mtfm.backend_support.provisioning.authority.GrantedRouter;
import com.mtfm.backend_support.provisioning.mapper.AuthorityMapper;
import com.mtfm.backend_support.provisioning.mapper.RouterMapper;
import com.mtfm.backend_support.provisioning.mapper.UserManageMapper;
import com.mtfm.core.ServiceCode;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.SecurityCode;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 权限组关系
 */
@Transactional(rollbackFor = Exception.class)
public class AuthorityGroupManager implements GroupAuthorityManager, MessageSourceAware {

    private MessageSourceAccessor messages = BackendSupportMessageSource.getAccessor();

    private final AuthorityMapper authorityMapper;

    private final UserManageMapper userManageMapper;

    private final RouterMapper routerMapper;

    public AuthorityGroupManager(AuthorityMapper authorityMapper, UserManageMapper userManageMapper, RouterMapper routerMapper) {
        this.authorityMapper = authorityMapper;
        this.userManageMapper = userManageMapper;
        this.routerMapper = routerMapper;
    }

    @Override
    public List<String> findAllGroups() {
        List<SolarGroup> groups = this.authorityMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isEmpty(groups)) {
            return new ArrayList<>();
        }
        return groups.stream()
                .filter(Objects::nonNull)
                .map(SolarGroup::getGroupName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        Assert.hasText(groupName, "groupName should have text");
        return this.authorityMapper.selectUsersInGroup(groupName);
    }

    @Override
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
        Assert.hasText(groupName, "groupName should have text");
        List<SolarGroup> groups = this.getGroups(groupName);
        if (!CollectionUtils.isEmpty(groups)) {
            throw new ServiceException(
                    this.messages.getMessage("GroupAuthorityManager.hadExistGroupName", new String[] {groupName}),
                    ServiceCode.DATA_EXIST.getCode()
            );
        }
        SolarGroup group = SolarGroup.withGroupName(groupName);
        this.authorityMapper.insert(group);
        if (!CollectionUtils.isEmpty(authorities)) {
            this.authorityMapper.insertGroupAuthorities(
                    group.getId(),
                    authorities.stream().map(item -> Long.parseLong(item.getAuthority())).collect(Collectors.toList())
            );
        }
    }

    @Override
    public void deleteGroup(String groupName) {
        List<SolarGroup> solarGroups = this.getGroups(groupName);
        if (!CollectionUtils.isEmpty(solarGroups)) {
            for (SolarGroup group : solarGroups) {
                this.authorityMapper.deleteAuthorities(group.getId(), null);
                this.authorityMapper.deleteById(group.getId());
            }
        }
        throw new ServiceException(
                this.messages.getMessage("GroupAuthorityManager.hadExistGroupName", new String[] {groupName}),
                ServiceCode.DATA_EXIST.getCode()
        );
    }

    @Override
    public void renameGroup(String oldName, String newName) {
        List<SolarGroup> groups = this.getGroups(oldName);
        if (CollectionUtils.isEmpty(groups)) {
            throw new ServiceException(
                    this.messages.getMessage("GroupAuthorityManager.notFoundGroupName", new String[] {oldName}),
                    ServiceCode.DATA_NOT_FOUND.getCode()
            );
        }
        List<SolarGroup> existGroup = this.getGroups(newName);
        if (!CollectionUtils.isEmpty(existGroup)) {
            throw new ServiceException(
                    this.messages.getMessage("GroupAuthorityManager.hadExistGroupName", new String[] {newName}),
                    ServiceCode.DATA_EXIST.getCode()
            );
        }
        for (SolarGroup group : groups) {
            group.setGroupName(newName);
            this.authorityMapper.updateById(group);
        }
    }

    @Override
    public void addUserToGroup(String username, String group) {
        Assert.hasText(username, "username should have text");
        Assert.hasText(group, "group should have text");
        List<SolarGroup> groups = this.getGroups(group);
        if (CollectionUtils.isEmpty(groups)) {
            throw new ServiceException(
                    this.messages.getMessage("GroupAuthorityManager.notFoundGroupName", new String[] {group}),
                    ServiceCode.DATA_NOT_FOUND.getCode()
            );
        }
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(username, BackendSupportIdentifier.DEFAULT);
        this.authorityMapper.insertUserToGroup(solarUser.getId(),
                groups.stream().map(SolarGroup::getId).collect(Collectors.toList()));
    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {
        Assert.hasText(username, "username should have text");
        Assert.hasText(groupName, "group should have text");
        List<SolarGroup> solarGroups = this.getGroups(groupName);
        if (CollectionUtils.isEmpty(solarGroups)) {
            throw new ServiceException(
                    this.messages.getMessage("GroupAuthorityManager.notFoundGroupName", new String[] {groupName}),
                    ServiceCode.DATA_NOT_FOUND.getCode()
            );
        }
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(username, BackendSupportIdentifier.DEFAULT);
        this.authorityMapper.deleteUserFromGroup(solarUser.getId(),
                solarGroups.stream().map(SolarGroup::getId).collect(Collectors.toList()));
    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        List<GrantedRouter> routers = this.routerMapper.selectRouters(groupName);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (CollectionUtils.isEmpty(routers)) {
            return authorities;
        }
        authorities = routers.stream().map(item -> (GrantedAuthority) item).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
        this.addGroupAuthorities(groupName, Collections.singletonList(authority));
    }

    @Override
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
        this.removeGroupAuthorities(groupName, Collections.singletonList(authority));
    }

    @Override
    public void addGroupAuthorities(String groupName, List<GrantedAuthority> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            return ;
        }
        List<SolarGroup> groups = this.getGroups(groupName);
        if (CollectionUtils.isEmpty(groups)) {
            throw new ServiceException(
                    this.messages.getMessage("GroupAuthorityManager.notFoundGroupName", new String[] {groupName}),
                    ServiceCode.DATA_NOT_FOUND.getCode()
            );
        }
        SolarGroup group = groups.get(0);
        this.authorityMapper.deleteAuthorities(group.getId(), null);
        this.authorityMapper.insertGroupAuthorities(
                group.getId(),
                authorities.stream().map(item -> Long.parseLong(item.getAuthority())).collect(Collectors.toList())
        );
    }

    @Override
    public void removeGroupAuthorities(String groupName, List<GrantedAuthority> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            return ;
        }
        List<SolarGroup> groups = this.getGroups(groupName);
        if (CollectionUtils.isEmpty(groups)) {
            throw new ServiceException(
                    this.messages.getMessage("GroupAuthorityManager.notFoundGroupName", new String[] {groupName}),
                    ServiceCode.DATA_NOT_FOUND.getCode()
            );
        }
        SolarGroup group = groups.get(0);
        this.authorityMapper.deleteAuthorities(
                group.getId(),
                authorities.stream().map(item -> Long.parseLong(item.getAuthority())).collect(Collectors.toList())
        );
    }

    @Override
    public List<GrantedAuthority> findGroupFromUser(String username) {
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(username, BackendSupportIdentifier.DEFAULT);
        if (solarUser == null) {
            throw new ServiceException(
                    this.messages.getMessage("UserDetailsManager.nonExist", "user not exist"),
                    SecurityCode.USER_NOT_FOUND.getCode()
            );
        }
        List<GrantedRouter> routers = this.routerMapper.selectRoutersByUser(solarUser.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (CollectionUtils.isEmpty(routers)) {
            return authorities;
        }
        authorities = routers.stream().map(item -> (GrantedAuthority) item).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public void removeGroupsFromUser(String username) {
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(username, BackendSupportIdentifier.DEFAULT);
        if (solarUser == null) {
            throw new ServiceException(
                    this.messages.getMessage("UserDetailsManager.nonExist", "user not exist"),
                    SecurityCode.USER_NOT_FOUND.getCode()
            );
        }
        this.authorityMapper.deleteUserFromGroup(solarUser.getId(), null);
    }

    @Override
    public void adduserToGroups(String username, List<GrantedAuthority> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            return ;
        }
        SolarUser solarUser = this.userManageMapper.selectUserByUsername(username, BackendSupportIdentifier.DEFAULT);
        if (solarUser == null) {
            throw new ServiceException(
                    this.messages.getMessage("UserDetailsManager.nonExist", "user not exist"),
                    SecurityCode.USER_NOT_FOUND.getCode()
            );
        }
        this.authorityMapper.deleteUserFromGroup(
                solarUser.getId(),
                authorities.stream().map(item -> Long.parseLong(item.getAuthority())).collect(Collectors.toList())
        );
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    private List<SolarGroup> getGroups(String groupName) {
        Assert.hasText(groupName, "groupName should have text");
        LambdaQueryWrapper<SolarGroup> query = new QueryWrapper<SolarGroup>().lambda().eq(SolarGroup::getGroupName, groupName);
        return this.authorityMapper.selectList(query);
    }

    protected AuthorityMapper getAuthorityMapper() {
        return authorityMapper;
    }

    protected UserManageMapper getUserManageMapper() {
        return userManageMapper;
    }
}
