package com.mtfm.tools.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Judge implements BaseEnum {

    YES(1, "YES"), NO(0, "NO");

    private int code;

    @JsonValue
    private String desc;

    Judge(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
