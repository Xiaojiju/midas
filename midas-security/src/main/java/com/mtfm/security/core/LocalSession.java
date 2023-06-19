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

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 当前请求会话
 * 该类为会话中的第二层，第一层为{@link UserSubject}，为当前的认证平台的会话信息
 * @author 一块小饼干
 * @since 1.0.0
 */
public class LocalSession {
    /**
     * 当前用户id
     */
    private String id;
    /**
     * 会话详情
     */
    private PlatformSession platformSession;

    private boolean signed;

    public LocalSession(String id, PlatformSession platformSession, boolean signed) {
        this.id = id;
        this.platformSession = platformSession;
        this.signed = signed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlatformSession getPlatformSession() {
        return platformSession;
    }

    public void setPlatformSession(PlatformSession platformSession) {
        this.platformSession = platformSession;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public static class LocalSessionBuilder {

        /**
         * 15分钟
         */
        private static final long EXPIRED_TIMESTAMP = 900L;
        /**
         * 30天
         */
        private static final long REFRESH_TIMESTAMP = 2592000L;

        private final String id;

        private final long curMills;

        private PlatformSession platformSession;

        private LocalSessionBuilder(String id) {
            this.id = id;
            this.curMills = System.currentTimeMillis();
        }

        public static LocalSessionBuilder withId(String id) {
            return new LocalSessionBuilder(id);
        }

        public LocalSessionBuilder withSessionKey(String sessionKey) {
            return withSessionKey(sessionKey, 0L, 0L);
        }

        public LocalSessionBuilder withSessionKey(String sessionKey, long expired, long refresh) {
            PlatformSession session = this.getPlatformSession();
            session.setSessionKey(sessionKey);
            if (expired <= 0L) {
                session.setExpiredTimestamps(curMills + EXPIRED_TIMESTAMP);
            } else {
                session.setExpiredTimestamps(curMills + expired);
            }
            if (refresh <= 0L) {
                session.setRefreshTimestamps(curMills + REFRESH_TIMESTAMP);
            } else {
                session.setRefreshTimestamps(curMills + refresh);
            }
            this.platformSession = session;
            return this;
        }

        public LocalSessionBuilder withPlatform(String ip, String ipAddr, String platform, String client) {
            SessionRequest sessionRequest = this.getSessionRequest();
            sessionRequest.setIp(ip);
            sessionRequest.setAddress(ipAddr);
            sessionRequest.setPlatform(platform);
            sessionRequest.setClient(client);
            PlatformSession session = this.getPlatformSession();
            session.setSessionRequest(sessionRequest);
            this.platformSession = session;
            return this;
        }

        public LocalSessionBuilder platformSession(PlatformSession session) {
            this.platformSession = session;
            return this;
        }

        public LocalSessionBuilder requestSession(SessionRequest sessionRequest) {
            PlatformSession session = this.getPlatformSession();
            session.setSessionRequest(sessionRequest);
            this.platformSession = session;
            return this;
        }

        public LocalSession build() {
            Assert.isTrue(StringUtils.hasText(this.id), "local session contain ip, could not be null");
            PlatformSession session = this.getPlatformSession();
            Assert.isTrue(StringUtils.hasText(session.getSessionKey()), "local session contain session key, could not be null");
            session.setSignTimestamps(curMills);
            if (session.getExpiredTimestamps() <= curMills) {
                session.setExpiredTimestamps(curMills + EXPIRED_TIMESTAMP);
            }
            if (session.getRefreshTimestamps() <= session.getExpiredTimestamps()) {
                session.setRefreshTimestamps(curMills + REFRESH_TIMESTAMP);
            }
            return new LocalSession(this.id, session, true);
        }

        private PlatformSession getPlatformSession() {
            if (this.platformSession == null) {
                return new PlatformSession();
            }
            return this.platformSession;
        }

        private SessionRequest getSessionRequest() {
            PlatformSession session = this.getPlatformSession();
            SessionRequest sessionRequest = session.getSessionRequest();
            if (sessionRequest == null) {
                sessionRequest = new SessionRequest();
                session.setSessionRequest(sessionRequest);
                this.platformSession = session;
            }
            return sessionRequest;
        }

    }
}
