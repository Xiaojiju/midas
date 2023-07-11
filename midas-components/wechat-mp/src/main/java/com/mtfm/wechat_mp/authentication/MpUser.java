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

import java.io.Serializable;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 小程序用户基本信息
 */
public class MpUser implements Serializable {

    private String nickname;

    private String avatar;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    public MpUser() {
    }

    public MpUser(String nickname, String avatar, Integer gender, String country, String province, String city) {
        this.nickname = nickname;
        this.avatar = avatar;
        this.gender = gender;
        this.country = country;
        this.province = province;
        this.city = city;
    }

    public static MpUserBuilder builder() {
        return new MpUserBuilder();
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

    public static class MpUserBuilder {

        private String nickname;
        private String avatar;
        private Integer gender;
        private String country;
        private String province;
        private String city;

        private MpUserBuilder() {

        }

        public MpUserBuilder withNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public MpUserBuilder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public MpUserBuilder withGender(Integer gender) {
            this.gender = gender;
            return this;
        }

        public MpUserBuilder from(String country, String province, String city) {
            this.country = country;
            this.province = province;
            this.city = city;
            return this;
        }

        public MpUser build() {
            return new MpUser(this.nickname, this.avatar, this.gender, this.country, this.province, this.city);
        }
    }

    public static class UserInfo {

        private String jsCode;
        private MpUser mpUser;

        public UserInfo() {
        }

        public UserInfo(String jsCode, MpUser mpUser) {
            this.jsCode = jsCode;
            this.mpUser = mpUser;
        }

        public String getJsCode() {
            return jsCode;
        }

        public void setJsCode(String jsCode) {
            this.jsCode = jsCode;
        }

        public MpUser getMpUser() {
            return mpUser;
        }

        public void setMpUser(MpUser mpUser) {
            this.mpUser = mpUser;
        }
    }
}
