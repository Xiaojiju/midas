package com.mtfm.weixin.qy;

public class SessionResultData extends ResultData {

    private String corpid;
    private String userid;
    private String session_key;

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    @Override
    public String toString() {
        return "SessionResultData{" +
                "errMsg+'" + this.getErrMsg() + '\'' +
                "errCode+'" + this.getErrCode() + '\'' +
                "corpid='" + corpid + '\'' +
                ", userid='" + userid + '\'' +
                ", session_key='" + session_key + '\'' +
                '}';
    }
}
