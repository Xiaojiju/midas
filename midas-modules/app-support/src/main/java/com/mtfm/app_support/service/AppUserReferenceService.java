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
package com.mtfm.app_support.service;

import com.mtfm.app_support.entity.AppUserReference;

import javax.validation.constraints.NotNull;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户用户名管理
 */
public interface AppUserReferenceService {
    /**
     * 创建用户用户名关系
     * @param appUserReference 用户名信息
     */
    void createAppUserUsername(AppUserReference appUserReference);

    /**
     * 修改用户名信息
     * @param appUserReference 用户名信息
     */
    void updateAppUserUsername(AppUserReference appUserReference);

    /**
     * 根据用户名获取用户名详细信息
     * @param username 用户名
     * @return 用户名信息
     */
    AppUserReference getOneByUsername(@NotNull String username);

    /**
     * 获取对应的认证方式的用户名信息
     * @param userId 用户id
     * @param identifier 用户认证方式
     * @return 用户名信息
     */
    AppUserReference getOneByUserIdentifier(@NotNull String userId,@NotNull String identifier);
}
