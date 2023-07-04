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
package com.mtfm.app_support.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.app_support.entity.AppUserSecret;
import com.mtfm.app_support.mapper.AppUserSecretMapper;
import com.mtfm.app_support.service.AppUserSecretService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户密钥业务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppUserSecretServiceImpl extends ServiceImpl<AppUserSecretMapper, AppUserSecret> implements AppUserSecretService {

    @Override
    public AppUserSecret getByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        QueryWrapper<AppUserSecret> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AppUserSecret::getUserId, userId);
        return this.getOne(queryWrapper);
    }
}
