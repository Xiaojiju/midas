package com.mtfm.backend_support.provisioning;

import com.mtfm.backend_support.provisioning.authority.SimpleUser;
import com.mtfm.core.util.page.PageQuery;
import com.mtfm.core.util.page.PageTemplate;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 管理员创建用户获取用户定义
 */
public interface ManageUserDetailsService<T> {

    void createOrUpdate(T userDetails);

    T loadUserDetails(String username);

    PageTemplate<SimpleUser> loadUsers(PageQuery query);

    void removeUser(String username);

}
