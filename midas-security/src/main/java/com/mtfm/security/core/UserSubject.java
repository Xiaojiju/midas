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

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会话第一层用户信息，主要包含多设备多平台信息
 * @author 一块小饼干
 * @since 1.0.0
 */
public class UserSubject implements Serializable {

    private String id;

    private List<PlatformSession> sessions;

    public UserSubject() {
    }

    public UserSubject(String id) {
        this(id, new ArrayList<>());
    }

    public UserSubject(String id, List<PlatformSession> sessions) {
        this.id = id;
        this.sessions = sessions;
    }

    public void addSession(PlatformSession session, boolean client) {
        if (CollectionUtils.isEmpty(this.sessions)) {
            this.sessions = new ArrayList<>();
        }
        String sessionKey = session.getSessionKey();
        int index = indexOf(sessionKey);
        if (index < 0) {
            // 找出是否有同平台的session
            if (client) {
                int clientIndex = indexOfClient(session.getSessionRequest().getClient());
                if (clientIndex >= 0) {
                    this.sessions.remove(clientIndex);
                }
            }
            this.sessions.add(session);
        } else {
            if (client) {
                int clientIndex = indexOfClient(session.getSessionRequest().getClient());
                if (clientIndex >= 0) {
                    this.sessions.remove(clientIndex);
                }
            }
            this.sessions.set(index, session);
        }
    }

    public void removeSession(String sessionKey) {
        if (CollectionUtils.isEmpty(this.sessions)) {
            return ;
        }
        int index = indexOf(sessionKey);
        if (index > -1) {
            this.sessions.remove(index);
        }
    }

    public PlatformSession getPlatformSession(String sessionKey) {
        int index = indexOf(sessionKey);
        if (index >= 0) {
            return this.sessions.get(index);
        }
        return null;
    }

    public boolean hasSession() {
        return StringUtils.hasText(this.id) && !CollectionUtils.isEmpty(this.sessions);
    }

    private int indexOf(String sessionKey) {
        if (!StringUtils.hasText(sessionKey)) {
            throw new NullPointerException("session key could not be null");
        }
        int index = -1;
        for (int i = 0; i < this.sessions.size(); i++) {
            PlatformSession platformSession = this.sessions.get(i);
            String existSessionKey = platformSession.getSessionKey();
            if (existSessionKey.equals(sessionKey)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int indexOfClient(String client) {
        if (!StringUtils.hasText(client)) {
            throw new NullPointerException("session key could not be null");
        }
        int index = -1;
        for (int i = 0; i < this.sessions.size(); i++) {
            PlatformSession platformSession = this.sessions.get(i);
            String clientKey = platformSession.getSessionRequest().getClient();
            if (clientKey.equals(client)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static UserSubject unSignedLocalUserSubject(String id) {
        return new UserSubject(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PlatformSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<PlatformSession> sessions) {
        this.sessions = sessions;
    }
}
