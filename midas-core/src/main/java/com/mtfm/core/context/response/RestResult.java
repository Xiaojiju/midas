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
package com.mtfm.core.context.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mtfm.core.CodeExpression;
import com.mtfm.core.ResultCode;

import java.io.Serializable;

/**
 * rest请求标准响应模板
 * @param <T> 响应对象类，响应对象类尽量不使用{@link java.util.Map}
 * @author 一块小饼干
 * @since 1.0.0
 */
public class RestResult<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 负载内容
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public RestResult() {
    }

    public RestResult(CodeExpression codeExpression) {
        this(null, codeExpression);
    }

    public RestResult(T data, CodeExpression codeExpression) {
        this(codeExpression.getCode(), codeExpression.getMessage(), data);
    }

    public RestResult(int code, String message) {
        this(code, message, null);
    }

    public RestResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RestResult<T> success() {
        return success(null);
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(data, ResultCode.OK);
    }

    public static <T> RestResult<T> error(String message) {
        return new RestResult<>(ResultCode.ERROR.getCode(), message);
    }

    public static <T> RestResult<T> error(int code, String message) {
        return new RestResult<>(code, message);
    }

    public static <T> RestResult<T> fail() {
        return complete(ResultCode.FAIL);
    }

    public static <T> RestResult<T> fail(String message) {
        return error(ResultCode.FAIL.getCode(), message);
    }

    public static <T> RestResult<T> complete(CodeExpression codeExpression) {
        return new RestResult<>(codeExpression.getCode(), codeExpression.getMessage());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * RestResult构造器
     * @param <T> 响应类
     * @author 一块小饼干
     * @since 1.0.0
     */
    private static class RestResultBuilder<T> {

        private int code;
        private String message;
        private T data;

        public RestResult<T> build() {
            return new RestResult<>(this.code, this.message, this.data);
        }

        public RestResultBuilder<T> setCode(int code) {
            this.code = code;
            return this;
        }

        public RestResultBuilder<T> setMessage(String message) {
            this.message = message;
            return this;
        }

        public RestResultBuilder<T> setData(T data) {
            this.data = data;
            return this;
        }
    }
}
