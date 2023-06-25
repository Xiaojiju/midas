package com.mtfm.weixin.mp;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class ErrorMsg implements Serializable {

    private String errcode;
    private String errmsg;

    public boolean success() {
        if (StringUtils.hasText(errcode)) {
            return String.valueOf(HttpStatus.OK.value()).equals(this.errcode);
        }
        return true;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
