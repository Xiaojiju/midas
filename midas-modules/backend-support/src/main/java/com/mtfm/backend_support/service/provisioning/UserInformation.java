package com.mtfm.backend_support.service.provisioning;

import com.mtfm.backend_support.entity.SolarBaseInfo;

import java.time.LocalDate;

public class UserInformation extends UserDetailSample {

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
    /**
     * 民族
     */
    private String nation;

    public SolarBaseInfo unCreatedBaseInfo() {
        return new SolarBaseInfo(null, this.nickname, this.avatar, this.gender, this.birth, this.nation, this.getId());
    }

    public SolarBaseInfo unCreatedBaseInfo(String uId) {
        return new SolarBaseInfo(null, this.nickname, this.avatar, this.gender, this.birth, this.nation, uId);
    }

    public SolarBaseInfo createdBaseInfo(String id) {
        return new SolarBaseInfo(id, this.nickname, this.avatar, this.gender, this.birth, this.nation, this.getId());
    }

    public SolarBaseInfo createdBaseInfo(String id, String uId) {
        return new SolarBaseInfo(id, this.nickname, this.avatar, this.gender, this.birth, this.nation, uId);
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
