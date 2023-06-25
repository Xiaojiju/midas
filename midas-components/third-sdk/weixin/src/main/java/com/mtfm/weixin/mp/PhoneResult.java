package com.mtfm.weixin.mp;

import java.io.Serializable;

public class PhoneResult extends ErrorMsg implements Serializable {

    private PhoneInfo phone_info;

    public PhoneInfo getPhone_info() {
        return phone_info;
    }

    public void setPhone_info(PhoneInfo phone_info) {
        this.phone_info = phone_info;
    }
}
