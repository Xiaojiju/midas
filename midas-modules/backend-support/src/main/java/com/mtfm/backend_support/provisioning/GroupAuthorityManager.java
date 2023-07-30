package com.mtfm.backend_support.provisioning;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.GroupManager;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 权限组关系定义
 */
public interface GroupAuthorityManager extends GroupManager {

    void addGroupAuthorities(String groupName, List<GrantedAuthority> authorities);

    void removeGroupAuthorities(String groupName, List<GrantedAuthority> authorities);

    void removeGroupsFromUser(String username);

    void adduserToGroups(String username, List<GrantedAuthority> authorities);

    List<GrantedAuthority> findGroupFromUser(String username);

}
