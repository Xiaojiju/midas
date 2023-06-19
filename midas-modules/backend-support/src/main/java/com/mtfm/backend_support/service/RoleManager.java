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
package com.mtfm.backend_support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.backend_support.entity.SolarRole;
import com.mtfm.backend_support.service.query.ValuePageQuery;
import com.mtfm.core.util.page.PageTemplate;

import java.util.Collection;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 角色管理
 */
public interface RoleManager extends IService<SolarRole> {
    /**
     * 保存角色
     * @param solarRole 角色信息
     * @return true为保存成功，反之则失败
     */
    boolean saveRole(SolarRole solarRole);

    /**
     * 修改角色
     * @param solarRole 角色信息
     * @return true为保存成功，反之则失败
     */
    boolean updateRole(SolarRole solarRole);

    /**
     * 删除角色
     * @param id 角色id
     * @return true为保存成功，反之则失败
     */
    boolean removeRole(String id);

    /**
     * 删除多个角色
     * @param ids 角色id集合
     * @return true为保存成功，反之则失败
     */
    boolean removeRoles(Collection<String> ids);

    /**
     * 分页查询
     * @param pageQuery 分页
     * @return {@link PageTemplate}默认页码为1，数量为12
     */
    PageTemplate<SolarRole> pageList(ValuePageQuery pageQuery);

}
