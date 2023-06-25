package com.mtfm.weixin.qy;

import java.io.Serializable;

public class CompanyToken implements Serializable {

    private String corpid;
    private String corpsecret;

    public CompanyToken() {
    }

    public CompanyToken(String corpid, String corpsecret) {
        this.corpid = corpid;
        this.corpsecret = corpsecret;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getCorpsecret() {
        return corpsecret;
    }

    public void setCorpsecret(String corpsecret) {
        this.corpsecret = corpsecret;
    }
}
