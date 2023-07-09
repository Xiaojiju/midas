package com.mtfm.backend_mall;

import com.mtfm.core.CodeExpression;

public enum MallCode implements CodeExpression {

    BRAND_NAME_EXIST(500300, "brand name exist");

    private final int code;
    private final String message;

    MallCode(int code, String message) {
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
