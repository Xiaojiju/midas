package com.mtfm.backend_support.provisioning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.backend_support.entity.SolarGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorityMapper extends BaseMapper<SolarGroup> {

    /**
     * 添加权限组关联的菜单
     * @param groupId 权限组id
     * @param authorities 代表权限的唯一标识
     * @return 影响行数
     */
    int insertGroupAuthorities(@Param("groupId") long groupId, @Param("authorities") List<Long> authorities);

    /**
     * 删除权限组包含的权限
     * @param groupId 权限组id
     * @param authorities 代表权限的唯一标识
     * @return 影响行数
     */
    int deleteAuthorities(@Param("groupId") long groupId, @Param("authorities") List<Long> authorities);
    /**
     * 通过权限组名称查询关联的用户
     * @param groupName 权限组名称
     * @return 用户名
     */
    List<String> selectUsersInGroup(String groupName);

    /**
     * 添加权限组给用户
     * @param userId 用户id
     * @param groups 权限组id
     * @return 影响行数
     */
    int insertUserToGroup(String userId, List<Long> groups);

    /**
     * 删除用户的权限组
     * @param userId 用户id
     * @param groups 权限组id
     * @return 影响行数
     */
    int deleteUserFromGroup(String userId, List<Long> groups);
}
