package com.mtfm.order.entity;

import com.mtfm.tools.enums.BaseEnum;

public enum OrderStatus implements BaseEnum {

    // 待付款、待发货、待收货、已完成、已取消
    WAITING_FOR_PAYMENT(0, "WAIT_FOR_PAYMENT"),

    WAITING_FOR_SHIPMENT(1, "WAITING_FOR_SHIPMENT"),

    WAITING_FOR_RECEIPT(2, "WAITING_FOR_RECEIPT"),

    COMPLETE_ORDER(3, "COMPLETE_ORDER"),

    CANCEL(4, "CANCEL");

    private final int code;

    private final String desc;

    OrderStatus(int code, String desc) {
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
