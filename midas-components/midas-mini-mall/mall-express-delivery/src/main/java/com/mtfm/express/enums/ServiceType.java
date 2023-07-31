package com.mtfm.express.enums;

import com.mtfm.tools.enums.BaseEnum;

public enum ServiceType implements BaseEnum {

    COLD_CHAIN(0, "COLD CHAIN"),

    GENERAL(1, "GENERAL");

    private final int code;

    private final String desc;

    ServiceType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
