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
package com.mtfm.backend_support.service.provisioning;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mtfm.backend_support.entity.SolarBaseInfo;
import com.mtfm.core.util.TimeConstants;
import com.mtfm.core.util.validator.ValidateGroup;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 一块小饼干
 * @since 1.0.0
 */
public class UserInformation extends UserDetailSample {

    @NotNull(groups = {ValidateGroup.Create.class}, message = "#UserInformation.nickname")
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别 0 男 1 女
     */
    private Integer gender;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = TimeConstants.Y_M_D)
    private LocalDate birth;

    public SolarBaseInfo unCreatedBaseInfo() {
        return new SolarBaseInfo(null, this.nickname, this.avatar, this.gender, this.birth, this.getId());
    }

    public SolarBaseInfo unCreatedBaseInfo(String uId) {
        return new SolarBaseInfo(null, this.nickname, this.avatar, this.gender, this.birth, uId);
    }

    public SolarBaseInfo createdBaseInfo(String id) {
        return new SolarBaseInfo(id, this.nickname, this.avatar, this.gender, this.birth, this.getId());
    }

    public SolarBaseInfo createdBaseInfo(String id, String uId) {
        return new SolarBaseInfo(id, this.nickname, this.avatar, this.gender, this.birth, uId);
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

}
