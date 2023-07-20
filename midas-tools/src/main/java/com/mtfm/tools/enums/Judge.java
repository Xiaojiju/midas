package com.mtfm.tools.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Judge implements BaseEnum {

    YES(1, "YES"), NO(0, "NO");

    private final int code;

    @JsonValue
    private final String desc;

    Judge(int code, String desc) {
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
