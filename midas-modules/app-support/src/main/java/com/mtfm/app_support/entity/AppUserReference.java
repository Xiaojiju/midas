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
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户账号
 */
@TableName(value = "app_user_reference", autoResultMap = true)
public class AppUserReference implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 认证方式
     */
    private String identifier;
    /**
     * 附带信息
     */
    @TableField("additional_key")
    private String additionalKey;
    /**
     * 是否认证
     */
    @TableField(value = "validated", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge validated;
    /**
     * 认证时间
     */
    @TableField("validated_time")
    private LocalDateTime validatedTime;
    /**
     * 过期时间
     */
    @TableField("expired_time")
    private LocalDateTime expiredTime;
    /**
     * 可以用户登陆
     */
    @TableField(value = "login_access", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge loginAccess;
    /**
     * 可以用于密钥认证
     */
    @TableField(value = "secret_access", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge secretAccess;
    /**
     * 是否为第三方
     */
    @TableField(value = "third_part", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge thirdPart;

    @TableField("user_id")
    private String userId;

    public AppUserReference() {
    }

    public AppUserReference(String id, String username, String identifier, String additionalKey, Judge validated,
                            LocalDateTime validatedTime, LocalDateTime expiredTime, Judge loginAccess, Judge secretAccess,
                            Judge thirdPart, String userId) {
        this.id = id;
        this.username = username;
        this.identifier = identifier;
        this.additionalKey = additionalKey;
        this.validated = validated;
        this.validatedTime = validatedTime;
        this.expiredTime = expiredTime;
        this.loginAccess = loginAccess;
        this.secretAccess = secretAccess;
        this.thirdPart = thirdPart;
        this.userId = userId;
    }

    public static AppUserReferenceBuilder builder(String userId) {
        return AppUserReferenceBuilder.uncreated(userId);
    }

    public static AppUserReferenceBuilder builder(String id, String userId) {
        return AppUserReferenceBuilder.created(id, userId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAdditionalKey() {
        return additionalKey;
    }

    public void setAdditionalKey(String additionalKey) {
        this.additionalKey = additionalKey;
    }

    public Judge getValidated() {
        return validated;
    }

    public void setValidated(Judge validated) {
        this.validated = validated;
    }

    public LocalDateTime getValidatedTime() {
        return validatedTime;
    }

    public void setValidatedTime(LocalDateTime validatedTime) {
        this.validatedTime = validatedTime;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Judge getLoginAccess() {
        return loginAccess;
    }

    public void setLoginAccess(Judge loginAccess) {
        this.loginAccess = loginAccess;
    }

    public Judge getSecretAccess() {
        return secretAccess;
    }

    public void setSecretAccess(Judge secretAccess) {
        this.secretAccess = secretAccess;
    }

    public Judge getThirdPart() {
        return thirdPart;
    }

    public void setThirdPart(Judge thirdPart) {
        this.thirdPart = thirdPart;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class AppUserReferenceBuilder {

        private final String id;
        private String username;
        private String identifier;
        private String additionalKey;
        private Judge validated;
        private LocalDateTime validatedTime;
        private LocalDateTime expiredTime;
        private Judge loginAccess;
        private Judge secretAccess;
        private Judge thirdPart;
        private final String userId;

        private AppUserReferenceBuilder(String id, String userId) {
            this.id = id;
            this.userId = userId;
        }

        private static AppUserReferenceBuilder uncreated(String userId) {
            return new AppUserReferenceBuilder(null, userId);
        }

        private static AppUserReferenceBuilder created(String id, String userId) {
            return new AppUserReferenceBuilder(id, userId);
        }

        public AppUserReferenceBuilder withUsername(String username) {
            this.username = username;
            this.identifier = "DEFAULT";
            return this;
        }

        public AppUserReferenceBuilder withUsername(String username, String identifier) {
            this.username = username;
            this.identifier = identifier;
            return this;
        }

        public AppUserReferenceBuilder withAdditionalKey(String additionalKey) {
            this.additionalKey = additionalKey;
            return this;
        }

        public AppUserReferenceBuilder validate(boolean validated) {
            if (validated) {
                this.validated = Judge.YES;
                this.validatedTime = LocalDateTime.now();
            }
            return this;
        }

        public AppUserReferenceBuilder expiredAt(LocalDateTime expiredTime) {
            this.expiredTime = expiredTime;
            return this;
        }

        public AppUserReferenceBuilder enableLogin(boolean enable) {
            if (enable) {
                this.loginAccess = Judge.YES;
            } else {
                this.loginAccess = Judge.NO;
            }
            return this;
        }

        public AppUserReferenceBuilder enableUsingSecretAuthenticated(boolean enable) {
            if (enable) {
                this.secretAccess = Judge.YES;
            } else {
                this.secretAccess = Judge.NO;
            }
            return this;
        }

        public AppUserReferenceBuilder isThirdPart(boolean enable) {
            if (enable) {
                this.thirdPart = Judge.YES;
            } else {
                this.thirdPart = Judge.NO;
            }
            return this;
        }

        public AppUserReference build() {
            Assert.isTrue(StringUtils.hasText(this.username), "username must not be null or empty");
            if (this.loginAccess == null) {
                this.loginAccess = Judge.YES;
            }
            if (this.thirdPart == null) {
                this.thirdPart = Judge.NO;
            }
            if (this.validated == null) {
                this.validated = Judge.YES;
                this.validatedTime = LocalDateTime.now();
            }
            if (this.secretAccess == null) {
                this.secretAccess = Judge.YES;
            }
            return new AppUserReference(this.id, this.username, this.identifier, this.additionalKey, this.validated,
                    this.validatedTime, this.expiredTime, this.loginAccess, this.secretAccess, this.thirdPart, this.userId);
        }
    }
}
