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
package com.mtfm.core;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 通用业务错误码
 */
public enum ServiceCode implements CodeExpression {

    DATA_EXIST(500900, "data exist"),
    DATA_NOT_FOUND(500901, "data not found"),
    DELETE_FAIL(500901, "data remove fail");

    private final int code;
    private final String message;

    ServiceCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
