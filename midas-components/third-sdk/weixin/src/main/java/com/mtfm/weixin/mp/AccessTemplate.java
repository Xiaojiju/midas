package com.mtfm.weixin.mp;

import java.io.Serializable;

public class AccessTemplate implements Serializable {

    private String appid;
    private String secret;
    private String grant_type;

    public AccessTemplate() {
    }

    public AccessTemplate(String appid, String secret, String grant_type) {
        this.appid = appid;
        this.secret = secret;
        this.grant_type = grant_type;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getGrant_type() {
        return grant_type;
    }
}
