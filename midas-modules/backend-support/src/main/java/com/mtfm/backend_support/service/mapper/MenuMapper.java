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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.backend_support.entity.SolarMenu;
import com.mtfm.backend_support.service.provisioning.GrantedMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 菜单操作Mapper
 */
@Mapper
public interface MenuMapper extends BaseMapper<SolarMenu> {

    /**
     * 查询角色关联的菜单
     * @param role 角色Id
     * @return 菜单
     */
    List<GrantedMenu> selectMenusByRoleId(String roleId);
    /**
     * 查询角色关联的菜单
     * @param roles 角色集合
     * @return 菜单
     */
    List<GrantedMenu> selectMenusByRoleIds(Collection<String> roles);

    /**
     * 查询所有的菜单
     * @return 所有的菜单
     */
    List<GrantedMenu> selectMenus();

    /**
     * 添加角色关联的菜单
     * @param roleId 角色Id
     * @param menuIds 菜单id
     * @return 影响行数
     */
    int insertMenusOnRole(@Param("roleId") String roleId,@Param("menuIds") List<String> menuIds);
}
