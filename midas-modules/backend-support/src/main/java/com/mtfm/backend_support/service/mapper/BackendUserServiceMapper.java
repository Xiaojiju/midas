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

import com.mtfm.backend_support.service.provisioning.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 后台用户
 */
@Mapper
public interface BackendUserServiceMapper {

    /**
     * 查询用户详情
     * @param referenceKey 用户关系key
     * @return {@link UserDto} 用户详细信息
     */
    UserDto selectUserByReferenceKey(@Param("referenceKey") String referenceKey);

//    /**
//     * 查询用户关联信息
//     * @param referenceKey 用户关系key
//     * @param identifier 关联信息类型
//     * @return {@link SolarUserReference} 用户关联信息
//     */
//    SolarUserReference selectReferenceByKey(@Param("referenceKey") String referenceKey, @Param("identifier") String identifier);
//
//    /**
//     * 查询用户关联信息
//     * @param userId 用户id
//     * @param identifier 关联信息类型
//     * @return {@link SolarUserReference} 用户关联信息
//     */
//    SolarUserReference selectReferenceByUserId(@Param("userId") String userId, @Param("identifier") String identifier);

}
