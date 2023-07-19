package com.mtfm.app_support.web.response;

import java.io.Serializable;

public class PasswordExist implements Serializable {

    private boolean exist;

    public PasswordExist() {
    }

    public PasswordExist(boolean exist) {
        this.exist = exist;
    }

    public static PasswordExist exist(boolean exist) {
        return new PasswordExist(exist);
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
