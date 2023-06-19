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
import com.mtfm.security.config.WebSecurityProperties;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户基本信息
 * @author 一块小饼干
 * @since 1.0.0
 */
@TableName("solar_user")
public class SolarUser extends BaseModel<SolarUser> implements Serializable {

    @TableId(value = "u_id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 账号锁定
     */
    @TableField(value = "locked", jdbcType = JdbcType.INTEGER, javaType = true, typeHandler = CommonEnumTypeHandler.class)
    private Judge locked;
    /**
     * 过期时间
     * 如果{@link WebSecurityProperties#isEnableAccountExpired()}设定为允许账号过期，则与当前时间进行比较，如果小于当前时间则过期，不
     * 允许认证登陆，反之则不进行操作。默认为空，如果修改配置为true，则在登陆认证的时候，进行懒处理的方式进行设定默认过期时间。
     */
    @TableField("expired_time")
    private LocalDateTime expiredTime;

    public SolarUser() {
    }

    public SolarUser(String id, Judge locked, LocalDateTime expiredTime) {
        this.id = id;
        this.locked = locked;
        this.expiredTime = expiredTime;
    }

    public static SolarUser newInstance() {
        return expiredUser(null);
    }

    public static SolarUser expiredUser(LocalDateTime expiredTime) {
        return new SolarUser(null, Judge.NO, expiredTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Judge getLocked() {
        return locked;
    }

    public void setLocked(Judge locked) {
        this.locked = locked;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }
}
