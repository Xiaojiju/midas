package com.mtfm.app_support;

import com.mtfm.core.CodeExpression;

public enum AppSupportCode implements CodeExpression {

    ;

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
