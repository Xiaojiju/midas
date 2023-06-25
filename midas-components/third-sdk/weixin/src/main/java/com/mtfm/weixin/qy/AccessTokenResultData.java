package com.mtfm.weixin.qy;

public class AccessTokenResultData extends ResultData {

    private String access_token;
    private String expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "AccessTokenResultData{" +
                "errMsg='" + this.getErrMsg() + '\'' +
                "errCode='" + this.getErrCode() + '\'' +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}
