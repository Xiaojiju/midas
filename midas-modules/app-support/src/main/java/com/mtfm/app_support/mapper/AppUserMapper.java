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
package com.mtfm.app_support.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.app_support.entity.AppAccount;
import com.mtfm.security.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户账号Mapper
 */
@Mapper
public interface AppUserMapper extends BaseMapper<AppAccount> {
    /**
     * 查询用户详情
     * @param referenceKey 用户关系key
     * @return {@link UserDto} 用户详细信息
     */
    AppUser selectUserByReferenceKey(@Param("referenceKey") String referenceKey);
}
