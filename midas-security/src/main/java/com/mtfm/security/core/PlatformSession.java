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
package com.mtfm.security.core;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public class PlatformSession implements Serializable {

    /**
     * 会话
     */
    private String sessionKey;

    /**
     * 过期时间
     */
    private long expiredTimestamps;

    /**
     * 刷新时间
     */
    private long refreshTimestamps;

    /**
     * 签发时间
     */
    private long signTimestamps;

    /**
     * 权限
     */
    private List<GrantedAuthority> permissions;

    /**
     * 请求标志
     */
    private SessionRequest sessionRequest;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public long getExpiredTimestamps() {
        return expiredTimestamps;
    }

    public void setExpiredTimestamps(long expiredTimestamps) {
        this.expiredTimestamps = expiredTimestamps;
    }

    public long getRefreshTimestamps() {
        return refreshTimestamps;
    }

    public void setRefreshTimestamps(long refreshTimestamps) {
        this.refreshTimestamps = refreshTimestamps;
    }

    public long getSignTimestamps() {
        return signTimestamps;
    }

    public void setSignTimestamps(long signTimestamps) {
        this.signTimestamps = signTimestamps;
    }

    public List<GrantedAuthority> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<GrantedAuthority> permissions) {
        this.permissions = permissions;
    }

    public SessionRequest getSessionRequest() {
        return sessionRequest;
    }

    public void setSessionRequest(SessionRequest sessionRequest) {
        this.sessionRequest = sessionRequest;
    }

    @Override
    public String toString() {
        return "PlatformSession{" +
                "sessionKey='" + sessionKey + '\'' +
                ", expiredTimestamps=" + expiredTimestamps +
                ", refreshTimestamps=" + refreshTimestamps +
                ", signTimestamps=" + signTimestamps +
                ", permissions=" + permissions +
                ", sessionRequest=" + sessionRequest +
                '}';
    }
}
