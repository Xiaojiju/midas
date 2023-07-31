package com.mtfm.order.entity;

import com.mtfm.tools.enums.BaseEnum;

public enum PaymentMethod implements BaseEnum {

    BALANCE_PAY(0, "BALANCE_PAYMENT"),

    WECHAT_PAY(1, "WECHAT_PAY");

    private final int code;

    private final String desc;

    PaymentMethod(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
