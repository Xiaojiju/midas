package com.mtfm.backend_support.provisioning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtfm.backend_support.entity.SolarUser;
import com.mtfm.backend_support.provisioning.authority.SimpleUser;
import com.mtfm.backend_support.provisioning.authority.SimpleUserProfile;
import com.mtfm.security.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserManageMapper extends BaseMapper<SolarUser> {

    /**
     * 查询用户详情
     * @param referenceKey 用户关系key
     * @return {@link AppUser} 用户详细信息
     */
    AppUser selectCurrentUserDetailsByUsername(@Param("username") String username);

    SolarUser selectUserByUsername(@Param("username") String username, @Param("identifier") String identifier);

    SimpleUserProfile selectDetails(@Param("username") String username);

    Page<SimpleUser> selectUserPage(Page<SimpleUser> page, @Param("username") String username, @Param("nickname") String nickname);

}
