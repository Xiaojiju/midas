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
package com.mtfm.backend_support.service.mapper;

import com.mtfm.backend_support.entity.SolarRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户角色Mapper
 */
@Mapper
public interface UserRoleMapper {
    /**
     * 查询用户所有角色
     * @param userId 用户id
     * @return 角色集合
     */
    List<SolarRole> selectRoleIds(String userId);

    /**
     * 删除用户角色
     * @param userId 用户id
     * @param roles 角色id
     * @return 影响行数
     */
    int deleteRoles(@Param("userId") String userId, @Param("roles") Collection<String> roles);

    /**
     * 添加用户角色
     * 用户角色表中用户id与角色id含有唯一约束，如果已经存在，则会被忽略；
     * @param userId 用户id
     * @param roles 角色id
     * @return 影响行数
     */
    int insertRoles(@Param("userId") String userId, @Param("roles") Collection<String> roles);

}
