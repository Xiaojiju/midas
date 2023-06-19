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
package com.mtfm.security;

import com.mtfm.core.CodeExpression;

/**
 * 响应错误码
 * @author 一块小饼干
 * @since 1.0.0
 */
public enum SecurityCode implements CodeExpression {

    //------------------- 授权 ---------------------
    /**
     * 密码错误
     */
    BAD_CREDENTIAL(500100, "Bad credentials"),
    /**
     * 没有权限
     */
    NO_AUTHORITIES(500101, "No authorities"),
    /**
     * 认证过期
     */
    HAS_EXPIRED(500102, "has expired"),
    /**
     * 找不到用户
     */
    USER_NOT_FOUND(500103, "not found"),
    /**
     * 被锁定
     */
    LOCKED(500104, "locked"),

    //------------------- 用户信息 ---------------------
    /**
     * 用户名存在
     */
    USERNAME_EXIST(500105, "username had exist");

    private final int code;
    private final String message;

    SecurityCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
