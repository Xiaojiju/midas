package com.mtfm.weixin.qy;

import java.io.Serializable;

public class SessionParams implements Serializable {

    private String access_token;
    private String js_code;
    private String grant_type;

    public SessionParams() {
    }

    public SessionParams(String access_token, String js_code, String grant_type) {
        this.access_token = access_token;
        this.js_code = js_code;
        this.grant_type = grant_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getJs_code() {
        return js_code;
    }

    public void setJs_code(String js_code) {
        this.js_code = js_code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
