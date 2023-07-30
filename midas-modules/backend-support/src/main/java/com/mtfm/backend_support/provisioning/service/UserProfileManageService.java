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
package com.mtfm.backend_support.provisioning.service;

import com.mtfm.backend_support.entity.SolarUserProfile;
import com.mtfm.backend_support.provisioning.UserProfileManager;
import com.mtfm.backend_support.provisioning.mapper.UserProfileMapper;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户个人信息
 */
public class UserProfileManageService implements UserProfileManager {

    private final UserProfileMapper userProfileMapper;

    public UserProfileManageService(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public SolarUserProfile getProfile(String username) {
        return this.userProfileMapper.selectProfileByUsername(username);
    }

    @Override
    public void saveProfile(SolarUserProfile profile) {
        this.userProfileMapper.insert(profile);
    }

    @Override
    public void updateProfile(SolarUserProfile profile) {
        this.userProfileMapper.updateById(profile);
    }
}
