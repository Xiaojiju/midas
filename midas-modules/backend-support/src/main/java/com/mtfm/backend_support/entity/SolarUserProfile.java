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
package com.mtfm.backend_support.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mtfm.tools.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 用户基本信息
 * @author 一块小饼干
 * @since 1.0.0
 */
@TableName("solar_user_profile")
public class SolarUserProfile implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 昵称
     */
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
    private LocalDate birth;

    @TableField("pinyin_letter")
    private String pinyinLetter;
    /**
     * 关联用户id
     */
    @TableField("u_id")
    private String uId;

    public SolarUserProfile() {
    }

    public SolarUserProfile(String id, String nickname, String avatar, Integer gender, LocalDate birth, String pinyinLetter, String uId) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.gender = gender;
        this.birth = birth;
        this.pinyinLetter = pinyinLetter;
        this.uId = uId;
    }

    public static BaseInfoBuilder builder(String uId) {
        return new BaseInfoBuilder(uId);
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

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getPinyinLetter() {
        return pinyinLetter;
    }

    public void setPinyinLetter(String pinyinLetter) {
        this.pinyinLetter = pinyinLetter;
    }

    public static class BaseInfoBuilder {

        private String id;

        private String nickname;

        private String avatar;

        private Integer gender;

        private LocalDate birth;

        private String pinyinLetter;

        private final String uId;

        private BaseInfoBuilder(String uId) {
            this.uId = uId;
        }

        public BaseInfoBuilder(SolarUserProfile solarBaseInfo) {
            this.id = solarBaseInfo.id;
            this.uId = solarBaseInfo.uId;
            this.birth = solarBaseInfo.birth;
            this.gender = solarBaseInfo.gender;
            this.avatar = solarBaseInfo.avatar;
            this.nickname = solarBaseInfo.nickname;
            this.pinyinLetter = solarBaseInfo.pinyinLetter;
        }

        public BaseInfoBuilder index(String id) {
            this.id = id;
            return this;
        }

        public BaseInfoBuilder withNickname(String nickname) {
            this.nickname = nickname;
            if (StringUtils.hasText(nickname)) {
                this.pinyinLetter = StringUtils.chineseLetters(nickname, true);
            }
            return this;
        }

        public BaseInfoBuilder withGender(Integer gender) {
            this.gender = gender;
            return this;
        }

        public BaseInfoBuilder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public SolarUserProfile build() {
            return new SolarUserProfile(this.id, this.nickname, this.avatar, this.gender, this.birth, this.pinyinLetter, this.uId);
        }
    }
}
