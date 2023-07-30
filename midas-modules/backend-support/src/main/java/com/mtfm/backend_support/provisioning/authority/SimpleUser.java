package com.mtfm.backend_support.provisioning.authority;

import com.mtfm.tools.enums.Judge;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SimpleUser implements Serializable {

    private String username;

    private String nickname;

    private Integer gender;

    private String avatarUrl;

    private LocalDate birth;

    private Judge accountLocked;

    private LocalDateTime expiredTime;

    private LocalDateTime createTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Judge getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Judge accountLocked) {
        this.accountLocked = accountLocked;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
