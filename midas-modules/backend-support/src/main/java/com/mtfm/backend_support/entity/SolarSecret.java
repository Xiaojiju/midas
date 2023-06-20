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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 秘钥
 * 出于对多登陆方式的拓展，更方便拓展其他的认证方式，将密钥单独储存，更新密钥时仅需要更新一行数据，相对于账号密码在一张表中的方式，更易于维护。
 * @author 一块小饼干
 * @since 1.0.0
 */
@TableName("solar_secret")
public class SolarSecret {

    @TableId(value = "s_id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 账号密钥
     */
    @TableField("secret_key")
    private String secret;

    /**
     * 盐
     */
    @TableField("secret_salt")
    private String salt;

    @TableField("u_id")
    private String uId;

    public SolarSecret() {
    }

    private SolarSecret(String id, String secret, String salt, String uId) {
        this.id = id;
        this.secret = secret;
        this.salt = salt;
        this.uId = uId;
    }

    public static SecretBuilder builder(String uId) {
        return new SecretBuilder(uId);
    }

    public static SecretBuilder builder(SolarSecret secret) {
        return new SecretBuilder(secret);
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "SolarSecret{" +
                "id='" + id + '\'' +
                ", secret='[protected]" +
                ", salt='" + salt + '\'' +
                ", uId='" + uId + '\'' +
                '}';
    }

    public static class SecretBuilder {

        private String id;
        private String secret;
        private String salt;
        private final String uId;

        private SecretBuilder(SolarSecret secret) {
            this.id = secret.id;
            this.secret = secret.secret;
            this.salt = secret.salt;
            this.uId = secret.uId;
        }

        private SecretBuilder(String uId) {
            this.uId = uId;
        }

        public SolarSecret build() {
            Assert.isTrue(StringUtils.hasText(this.secret), "secret could not null");
            return new SolarSecret(this.id, this.secret, this.salt, this.uId);
        }

        public SecretBuilder updateWith(String id) {
            this.id = id;
            return this;
        }

        // 如果密码为空，则不进行更改
        public SecretBuilder makeItSecret(String secret, String salt, PasswordEncoder passwordEncoder) {
            Assert.isTrue(StringUtils.hasText(secret), "secret could not be null");
            if (StringUtils.hasText(salt)) {
                secret = secret + salt;
                this.salt = salt;
            }
            this.secret = passwordEncoder.encode(secret);
            return this;
        }

    }
}
