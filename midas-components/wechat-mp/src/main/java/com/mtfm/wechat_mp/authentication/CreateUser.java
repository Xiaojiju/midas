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
package com.mtfm.wechat_mp.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 小程序创建用户
 */
public class CreateUser extends MpUser implements UserDetails, Serializable {

    private String openId;
    private String unionId;

    public CreateUser(String openId, String unionId, MpUser mpUser) {
        this.openId = openId;
        this.unionId = unionId;
        if (mpUser != null) {
            super.setNickName(mpUser.getNickName());
            super.setAvatarUrl(mpUser.getAvatarUrl());
            super.setGender(mpUser.getGender());
            super.setCountry(mpUser.getCountry());
            super.setProvince(mpUser.getProvince());
            super.setCity(mpUser.getCity());
        }
    }

    public CreateUser(String nickname, String avatar, Integer gender, String country, String province, String city, String openId, String unionId) {
        super(nickname, avatar, gender, country, province, city);
        this.openId = openId;
        this.unionId = unionId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.openId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
