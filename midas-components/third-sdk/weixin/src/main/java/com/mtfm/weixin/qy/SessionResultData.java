/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.weixin.qy;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 会话响应
 */
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
