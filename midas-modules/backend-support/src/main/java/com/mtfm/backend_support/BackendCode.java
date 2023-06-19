package com.mtfm.backend_support;

import com.mtfm.core.CodeExpression;

public enum BackendCode implements CodeExpression {

    // ROLE
    ROLE_NAME_DUPLICATE(500200, "role name duplicate");

    private final int code;
    private final String message;

    BackendCode(int code, String message) {
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
}
