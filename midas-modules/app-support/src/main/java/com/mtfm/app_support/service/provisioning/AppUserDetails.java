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
package com.mtfm.app_support.service.provisioning;

import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.security.AppUser;
import com.mtfm.tools.enums.Judge;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户详细信息
 */
public class AppUserDetails extends AppUser implements Serializable {

    private AppUserBaseInfo appUserBaseInfo;

    public AppUserDetails() {
        this(null);
    }

    public AppUserDetails(AppUserBaseInfo appUserBaseInfo) {
        this(appUserBaseInfo, new AppUser());
    }

    public AppUserDetails(AppUserBaseInfo appUserBaseInfo, AppUser appUser) {
        this(appUser.getId(), appUser.getUsername(), appUser.getIdentifier(), appUser.getPassword(), appUser.getSecretExpiredTime(),
                appUser.getAccountExpiredTime(), appUser.getValidated(), appUser.getAccountLocked(), appUser.getAdditionalKey(),
                appUser.getUsernameExpiredTime(), appUser.getSecretAccess(), appUser.getThirdPart(), appUser.getLoginAccess(),
                appUser.getAuthorities(), appUserBaseInfo);
    }

    public AppUserDetails(String id, String username, String identifier, String password, LocalDateTime secretExpiredTime,
                          LocalDateTime accountExpiredTime, Judge validated, Judge accountLocked, String additionalKey,
                          LocalDateTime usernameExpiredTime, Judge secretAccess, Judge thirdPart, Judge loginAccess,
                          List<GrantedAuthority> authorities, AppUserBaseInfo appUserBaseInfo) {
        super(id, username, identifier, password, secretExpiredTime, accountExpiredTime, validated, accountLocked,
                additionalKey, usernameExpiredTime, secretAccess, thirdPart, loginAccess, authorities);
        this.appUserBaseInfo = appUserBaseInfo;
    }

    public AppUserBaseInfo getAppUserBaseInfo() {
        return appUserBaseInfo;
    }

    public void setAppUserBaseInfo(AppUserBaseInfo appUserBaseInfo) {
        this.appUserBaseInfo = appUserBaseInfo;
    }
}
