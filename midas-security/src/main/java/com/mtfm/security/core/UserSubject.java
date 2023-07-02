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

import java.io.Serializable;
import java.util.Set;

/**
 * 保存到redis的中用户信息
 * @author 一块小饼干
 * @since 1.0.0
 */
public class UserSubject implements Serializable {

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
    private Set<String> permissions;

    /**
     * 请求标志
     */
    private SessionRequest sessionRequest;

    public UserSubject() {
    }

    public UserSubject(long expiredTimestamps, long refreshTimestamps, long signTimestamps,
                       Set<String> permissions, SessionRequest sessionRequest) {
        this.expiredTimestamps = expiredTimestamps;
        this.refreshTimestamps = refreshTimestamps;
        this.signTimestamps = signTimestamps;
        this.permissions = permissions;
        this.sessionRequest = sessionRequest;
    }

    public static UserSubjectBuilder builder() {
        return new UserSubjectBuilder();
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

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public SessionRequest getSessionRequest() {
        return sessionRequest;
    }

    public void setSessionRequest(SessionRequest sessionRequest) {
        this.sessionRequest = sessionRequest;
    }

    public static class UserSubjectBuilder {

        private long expiredTimestamps;
        private long refreshTimestamps;
        private long signTimestamps;
        private Set<String> permissions;
        private SessionRequest sessionRequest;

        private UserSubjectBuilder() {
        }

        public UserSubjectBuilder expiredAt(long expiredTimestamps) {
            this.expiredTimestamps = expiredTimestamps;
            return this;
        }

        public UserSubjectBuilder refreshIn(long refreshTimestamps) {
            this.refreshTimestamps = refreshTimestamps;
            return this;
        }

        public UserSubjectBuilder signAt(long signTimestamps) {
            this.signTimestamps = signTimestamps;
            return this;
        }

        public UserSubjectBuilder withPermissions(Set<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        public UserSubjectBuilder requestDetails(SessionRequest sessionRequest) {
            this.sessionRequest = sessionRequest;
            return this;
        }

        public UserSubjectBuilder withIp(String ip) {
            if (sessionRequest == null) {
                this.sessionRequest = new SessionRequest();
            }
            this.sessionRequest.setIp(ip);
            return this;
        }

        public UserSubject build() {
            return new UserSubject(this.expiredTimestamps, this.refreshTimestamps, this.signTimestamps,
                    this.permissions, this.sessionRequest);
        }
    }
}
