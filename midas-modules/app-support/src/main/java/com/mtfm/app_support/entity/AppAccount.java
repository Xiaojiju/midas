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
import com.mtfm.datasource.BaseModel;
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "app_account", autoResultMap = true)
public class AppAccount extends BaseModel<AppAccount> implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "locked", jdbcType = JdbcType.INTEGER, javaType = true, typeHandler = CommonEnumTypeHandler.class)
    private Judge locked;

    @TableField("expired_time")
    private LocalDateTime expiredTime;

    public AppAccount() {
    }

    public AppAccount(String id, Judge locked, LocalDateTime expiredTime) {
        this.id = id;
        this.locked = locked;
        this.expiredTime = expiredTime;
    }

    public static AppAccount uncreated(Judge locked, LocalDateTime expiredTime) {
        return new AppAccount(null, locked, expiredTime);
    }

    public static AppAccount created(String id, Judge locked, LocalDateTime expiredTime) {
        Assert.isTrue(StringUtils.hasText(id), "id could not be null or empty");
        return new AppAccount(id, locked, expiredTime);
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
