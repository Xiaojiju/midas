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
package com.mtfm.backend_support.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.backend_support.entity.SolarUserReference;
import com.mtfm.backend_support.service.UserReferenceManager;
import com.mtfm.backend_support.service.mapper.UserReferenceMapper;
import com.mtfm.tools.enums.Judge;
import org.springframework.util.StringUtils;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户名管理
 */
public class UserReferenceManagerService extends ServiceImpl<UserReferenceMapper, SolarUserReference> implements UserReferenceManager {

    @Override
    public void removeUser(String userId) {
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getuId, userId);
        this.remove(queryWrapper);
    }

    @Override
    public SolarUserReference getByReferenceKey(String referenceKey, String identifier) {
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getReferenceKey, referenceKey)
                .eq(SolarUserReference::getDeleted, Judge.NO)
                .eq(StringUtils.hasText(identifier), SolarUserReference::getIdentifier, identifier);
        return this.getOne(queryWrapper);
    }

    @Override
    public SolarUserReference getByUserId(String userId, String identifier) {
        QueryWrapper<SolarUserReference> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarUserReference::getuId, userId)
                .eq(SolarUserReference::getDeleted, Judge.NO)
                .eq(StringUtils.hasText(identifier), SolarUserReference::getIdentifier, identifier);
        return this.getOne(queryWrapper);
    }
}
