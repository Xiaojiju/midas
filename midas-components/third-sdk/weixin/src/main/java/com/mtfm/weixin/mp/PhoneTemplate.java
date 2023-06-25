package com.mtfm.weixin.mp;

import java.io.Serializable;

public class PhoneTemplate implements Serializable {

    private String code;

    public PhoneTemplate(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
