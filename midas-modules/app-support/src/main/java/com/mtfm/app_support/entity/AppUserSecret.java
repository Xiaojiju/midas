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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户密钥
 */
@TableName("app_user_secret")
public class AppUserSecret implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String secret;

    @TableField("expired_time")
    private LocalDateTime expiredTime;

    @TableField("user_id")
    private String userId;

    public AppUserSecret() {
    }

    public AppUserSecret(String id, String secret, LocalDateTime expiredTime, String userId) {
        this.id = id;
        this.secret = secret;
        this.expiredTime = expiredTime;
        this.userId = userId;
    }

    public static SecretBuilder builder(String userId) {
        return new SecretBuilder(userId);
    }

    public SecretBuilder builder() {
        Assert.isTrue(StringUtils.hasText(this.userId), "userId could not be null");
        return new SecretBuilder(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class SecretBuilder {

        private String id;
        private String secret;
        private LocalDateTime expiredTime;
        private final String userId;

        private SecretBuilder(AppUserSecret secret) {
            this.id = secret.id;
            this.secret = secret.secret;
            this.expiredTime = secret.expiredTime;
            this.userId = secret.userId;
        }

        private SecretBuilder(String userId) {
            this.userId = userId;
        }

        public AppUserSecret build() {
            Assert.isTrue(StringUtils.hasText(this.secret), "secret could not null");
            Assert.isTrue(StringUtils.hasText(this.userId), "userId could not be null");
            return new AppUserSecret(this.id, this.secret, this.expiredTime, this.userId);
        }

        public SecretBuilder updateWith(String id) {
            this.id = id;
            return this;
        }

        // 如果密码为空，则不进行更改
        public SecretBuilder makeItSecret(String secret, String salt, PasswordEncoder passwordEncoder) {
            if (StringUtils.hasText(salt)) {
                secret = secret + salt;
            }
            this.secret = passwordEncoder.encode(secret);
            return this;
        }

    }
}
