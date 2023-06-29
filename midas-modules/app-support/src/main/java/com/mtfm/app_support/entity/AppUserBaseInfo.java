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
package com.mtfm.app_support.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户基本信息
 */
@TableName("app_user_base_info")
public class AppUserBaseInfo implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String nickname;

    private String avatar;

    private Integer gender;

    private LocalDate birth;

    private String country;

    private String province;

    private String city;

    @TableField("user_id")
    private String userId;

    public AppUserBaseInfo() {
    }

    public AppUserBaseInfo(String id, String nickname, String avatar, Integer gender, LocalDate birth, String country,
                           String province, String city, String userId) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.gender = gender;
        this.birth = birth;
        this.country = country;
        this.province = province;
        this.city = city;
        this.userId = userId;
    }

    public static AppUserBaseInfoBuilder builder(AppUserBaseInfo appUserBaseInfo) {
        return new AppUserBaseInfoBuilder(appUserBaseInfo.getId(), appUserBaseInfo.getUserId())
                .withNickname(appUserBaseInfo.getNickname())
                .withGender(appUserBaseInfo.getGender())
                .withBirth(appUserBaseInfo.getBirth())
                .withAvatar(appUserBaseInfo.getAvatar())
                .whereFrom(appUserBaseInfo.getCountry(), appUserBaseInfo.getProvince(), appUserBaseInfo.getCity());
    }

    public static AppUserBaseInfoBuilder created(String id, String userId) {
        return new AppUserBaseInfoBuilder(id, userId);
    }

    public static AppUserBaseInfoBuilder uncreated(String userId) {
        return new AppUserBaseInfoBuilder(userId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class AppUserBaseInfoBuilder {

        private String id;

        private String nickname;

        private String avatar;

        private Integer gender;

        private LocalDate birth;

        private String country;

        private String province;

        private String city;

        private final String userId;

        private AppUserBaseInfoBuilder(String userId) {
            Assert.isTrue(StringUtils.hasText(userId), "userId could not be null or empty");
            this.userId = userId;
        }

        private AppUserBaseInfoBuilder(String id, String userId) {
            Assert.isTrue(StringUtils.hasText(id), "id could not be null or empty");
            this.id = id;
            Assert.isTrue(StringUtils.hasText(userId), "userId could not be null or empty");
            this.userId = userId;
        }

        public AppUserBaseInfoBuilder withNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public AppUserBaseInfoBuilder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public AppUserBaseInfoBuilder withGender(int gender) {
            this.gender = gender;
            return this;
        }

        public AppUserBaseInfoBuilder withBirth(LocalDate birth) {
            this.birth = birth;
            return this;
        }

        public AppUserBaseInfoBuilder whereFrom(String country, String province, String city) {
            this.country = country;
            this.province = province;
            this.city = city;
            return this;
        }

        public AppUserBaseInfo build() {
            return new AppUserBaseInfo(this.id, this.nickname, this.avatar, this.gender, this.birth, this.country,
                    this.province, this.city, this.userId);
        }
    }
}
