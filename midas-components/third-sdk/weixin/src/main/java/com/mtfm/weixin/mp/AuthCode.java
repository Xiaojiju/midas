package com.mtfm.weixin.mp;

import java.io.Serializable;

public class AuthCode extends AccessTemplate implements Serializable {

    private String js_code;

    public AuthCode(String appid, String secret, String grant_type, String js_code) {
        super(appid, secret, grant_type);
        this.js_code = js_code;
    }

    public static AuthCodeBuilder builder() {
        return new AuthCodeBuilder();
    }

    public String getJs_code() {
        return js_code;
    }

    public void setJs_code(String js_code) {
        this.js_code = js_code;
    }

    public static class AuthCodeBuilder {
        private String appid;
        private String secret;
        private String js_code;
        private String grant_type;

        public AuthCode build() {
            return new AuthCode(appid, secret, grant_type, js_code);
        }

        public AuthCodeBuilder setAppid(String appid) {
            this.appid = appid;
            return this;
        }

        public AuthCodeBuilder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public AuthCodeBuilder setJs_code(String js_code) {
            this.js_code = js_code;
            return this;
        }

        public AuthCodeBuilder setGrant_type(String grant_type) {
            this.grant_type = grant_type;
            return this;
        }
    }
}
