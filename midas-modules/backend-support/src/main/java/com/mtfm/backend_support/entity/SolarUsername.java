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
import com.mtfm.datasource.BaseModel;
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户关联信息
 * 主要是存放各个认证方式账号或者认证标识，中间属性包含一些认证方式有效性验证、认证过期以及是否用于认证登陆的判定。
 * 实体的设定对于特定的场景不太严谨或冗余，可以进行自行修改。
 * @author 一块小饼干
 * @since 1.0.0
 */
@TableName(value = "solar_user_reference", autoResultMap = true)
public class SolarUsername extends BaseModel<SolarUsername> implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 关联信息类型，账号认证的方式
     */
    @TableField("identifier")
    private String identifier;
    /**
     * 关联信息键，比如：账号，手机号，相当于用户名。
     * 设计这个属性原因是处于多登陆方式认证，方便拓展
     */
    @TableField("username")
    private String username;

    /**
     * 是否验证，标记认证方式是否经过验证
     */
    @TableField(value = "validated", jdbcType = JdbcType.INTEGER, javaType = true, typeHandler = CommonEnumTypeHandler.class)
    private Judge validated;

    /**
     * 关联后的键（主要用于第三方认证）
     */
    @TableField("after_reference_key")
    private String afterReferenceKey;

    /**
     * 认证方式的过期时间
     */
    @TableField("expired_time")
    private LocalDateTime expiredTime;

    /**
     * 认证方式的验证日期
     */
    @TableField("validated_time")
    private LocalDateTime validatedTime;

    /**
     * 是否允许使用该认证方式登陆
     */
    @TableField(value = "login_access", jdbcType = JdbcType.INTEGER, javaType = true, typeHandler = CommonEnumTypeHandler.class)
    private Judge loginAccess;

    /**
     * 标记是否为第三方认证信息
     */
    @TableField(value = "third_part", jdbcType = JdbcType.INTEGER, javaType = true, typeHandler = CommonEnumTypeHandler.class)
    private Judge thirdPart;

    /**
     * 关联用户id
     */
    @TableField("u_id")
    private String uId;

    public SolarUsername() {
    }

    public SolarUsername(String id, String identifier, String username, Judge validated, String afterReferenceKey,
                         LocalDateTime expiredTime, LocalDateTime validatedTime, Judge loginAccess, Judge thirdPart,
                         String uId) {
        this.id = id;
        this.identifier = identifier;
        this.username = username;
        this.validated = validated;
        this.afterReferenceKey = afterReferenceKey;
        this.expiredTime = expiredTime;
        this.validatedTime = validatedTime;
        this.loginAccess = loginAccess;
        this.thirdPart = thirdPart;
        this.uId = uId;
    }

    public static UserReferenceBuilder withUId(String uId) {
        return new UserReferenceBuilder(uId);
    }

    public static UserReferenceBuilder builder(SolarUsername solarUsername) {
        return new UserReferenceBuilder(solarUsername);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Judge getValidated() {
        return validated;
    }

    public void setValidated(Judge validated) {
        this.validated = validated;
    }

    public String getAfterReferenceKey() {
        return afterReferenceKey;
    }

    public void setAfterReferenceKey(String afterReferenceKey) {
        this.afterReferenceKey = afterReferenceKey;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public LocalDateTime getValidatedTime() {
        return validatedTime;
    }

    public void setValidatedTime(LocalDateTime validatedTime) {
        this.validatedTime = validatedTime;
    }

    public Judge getLoginAccess() {
        return loginAccess;
    }

    public void setLoginAccess(Judge loginAccess) {
        this.loginAccess = loginAccess;
    }

    public Judge getThirdPart() {
        return thirdPart;
    }

    public void setThirdPart(Judge thirdPart) {
        this.thirdPart = thirdPart;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public static class UserReferenceBuilder {

        private String id;
        private String identifier;
        private String username;
        private Judge validated;
        private String afterReferenceKey;
        private LocalDateTime expiredTime;
        private LocalDateTime validatedTime;
        private Judge loginAccess;
        private Judge thirdPart;
        private final String uId;

        private UserReferenceBuilder(String uId) {
            this.uId = uId;
        }

        private UserReferenceBuilder(SolarUsername solarUsername) {
            this.id = solarUsername.id;
            this.identifier = solarUsername.identifier;
            this.username = solarUsername.username;
            this.validated = solarUsername.validated;
            this.afterReferenceKey = solarUsername.afterReferenceKey;
            this.expiredTime = solarUsername.expiredTime;
            this.loginAccess = solarUsername.loginAccess;
            this.thirdPart = solarUsername.thirdPart;
            this.uId = solarUsername.uId;
            this.validatedTime = solarUsername.validatedTime;
        }

        public SolarUsername build() {
            Assert.isTrue(StringUtils.hasText(this.uId), "uId could not be null");
            Assert.isTrue(StringUtils.hasText(this.identifier), "user reference identifier field could not " +
                    "be null");
            Assert.isTrue(StringUtils.hasText(this.username), "user reference key field could not be null");
            if (this.loginAccess == null) {
                this.loginAccess = Judge.YES;
            }
            if (this.validated == null) {
                this.validated = Judge.NO;
            }
            if (this.thirdPart == null) {
                this.thirdPart = Judge.NO;
            }
            return new SolarUsername(this.id, this.identifier, this.username, this.validated,
                    this.afterReferenceKey, this.expiredTime, this.validatedTime, this.loginAccess,
                    this.thirdPart, this.uId);
        }

        public UserReferenceBuilder withReferenceKey(final String username) {
            if (StringUtils.hasText(username)) {
                this.username = username;
            }
            return this;
        }

        public UserReferenceBuilder identifyBy(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public UserReferenceBuilder hadValidated(Judge validated, LocalDateTime validatedTime, LocalDateTime expiredTime) {
            if (validated == Judge.YES) {
                Assert.isTrue(validatedTime != null, "if current reference key had validated, validated " +
                        "time should not be null");
                Assert.isTrue(expiredTime != null, "if current reference key had validated, expired " +
                        "time should not be null");
            }
            this.validated = validated;
            this.validatedTime = validatedTime;
            this.expiredTime = expiredTime;
            return this;
        }

        public UserReferenceBuilder bindAfter(String afterReferenceKey) {
            this.afterReferenceKey = afterReferenceKey;
            return this;
        }

        public UserReferenceBuilder allowable(Judge loginAccess) {
            this.loginAccess = loginAccess;
            return this;
        }

        public UserReferenceBuilder isThirdPart(Judge thirdPart) {
            this.thirdPart = thirdPart;
            return this;
        }
    }
}
