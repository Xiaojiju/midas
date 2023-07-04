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

import com.mtfm.backend_support.entity.SolarRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户角色管理
 */
public interface UserRoleManager {
    /**
     * 获取用户的所有角色
     * @param userId 用户id
     * @return 用户的所有角色
     */
    List<SolarRole> loadRoles(String userId);

    /**
     * 修改用户角色
     * @param userId 用户id
     * @param roleIds 角色id
     * @return true为修改成功，反之则为false
     */
    boolean modifyRoles(String userId, Collection<? extends GrantedAuthority> roles);

}
