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
package com.mtfm.app_support;

import com.mtfm.core.CodeExpression;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 业务码 500200
 */
public enum AppSupportCode implements CodeExpression {

    NONE_PASSWORD(500200, "none password"),

    CREATE_USERNAME_HAD_EXIST(500201, "the user already has a username with the same authentication " +
            "method and cannot be added again"),

    USERNAME_NAME_EXIST(500202, "username had exist"),

    UPDATE_USERNAME_EXIST_FOR_OTHER_AUTHENTICATION_METHOD(500203, "The same username already exists for " +
            "other authentication methods and cannot be added again"),

    USER_SECRET_EXIST(500204, "user password had exist"),

    USER_NOT_FOUND(500205, "could not found app user details");

    private final int code;

    private final String message;

    AppSupportCode(int code, String message) {
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
