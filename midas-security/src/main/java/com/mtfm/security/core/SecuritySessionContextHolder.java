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

import com.mtfm.core.context.HttpRequestHolder;
import com.mtfm.security.config.WebSecurityProperties;
import com.mtfm.security.context.AuthStyle;
import org.springframework.util.StringUtils;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;

/**
 * 用户会话操作类，将第一层{@link AnySessionContextHandler}中{@link UserSubject}解析出当前平台认证的信息，屏蔽直接操作远程缓存的用户总
 * 信息。
 * @author 一块小饼干
 * @since 1.0.0
 */
public class SecuritySessionContextHolder implements SessionContext<LocalSession> {

    private static final ThreadLocal<LocalSession> localSubject = new ThreadLocal<>();
    private RequestSession requestSession;
    private AnySessionContext<UserSubject> sessionContext;
    private LocalSessionProvider localSessionProvider;
    private WebSecurityProperties webSecurityProperties;

    public SecuritySessionContextHolder(AnySessionContext<UserSubject> sessionContext,
                                        LocalSessionProvider localSessionProvider,
                                        WebSecurityProperties webSecurityProperties) {
        this(new HttpRequestSessionHandler(), sessionContext, localSessionProvider, webSecurityProperties);
    }

    public SecuritySessionContextHolder(RequestSession requestSession,
                                        AnySessionContext<UserSubject> sessionContext,
                                        LocalSessionProvider localSessionProvider,
                                        WebSecurityProperties webSecurityProperties) {
        this.requestSession = requestSession;
        this.sessionContext = sessionContext;
        this.localSessionProvider = localSessionProvider;
        this.webSecurityProperties = webSecurityProperties;
    }

    /**
     * 更新LocalSession的信息
     * @param subject 当前的用户会话信息
     * 仅仅只需要更新当前会话的信息，根据配置{@link com.mtfm.security.config.WebSecurityProperties}中所获取的multiDevices属性，默认
     * 为single
     */
    @Override
    public void putSession(LocalSession subject) {
        if (subject == null) {
            throw new NullPointerException("could be set null to local session context");
        }
        String id = subject.getId();
        UserSubject session = this.sessionContext.getSession(id);
        if (session == null || !session.hasSession()) {
            session = UserSubject.unSignedLocalUserSubject(id);
        }
        if (!session.hasSession()) {
            session.addSession(subject.getPlatformSession(), false);
        } else {
            String multiDevices = webSecurityProperties.getMultiDevices();
            if (StringUtils.hasText(multiDevices)) {
                if (multiDevices.equals(AuthStyle.SINGLE.getValue())) {
                    session.setSessions(new ArrayList<>());
                    session.addSession(subject.getPlatformSession(), false);
                } else if (multiDevices.equals(AuthStyle.PLATFORM.getValue())) {
                    // 多设备登陆
                    session.addSession(subject.getPlatformSession(), true);
                } else {
                    session.addSession(subject.getPlatformSession(), false);
                }
            } else {
                session.setSessions(new ArrayList<>());
                session.addSession(subject.getPlatformSession(), false);
            }
        }
        localSubject.set(subject);
        this.sessionContext.putSession(id, session);
    }

    /**
     * 获取当前认证的会话，如果当前会话已经过期，则根据{@link com.mtfm.security.config.WebAuthProperties}的刷新策略进行刷新当前会话
     */
    @Override
    public LocalSession getSession() {
        LocalSession localSession = localSubject.get();
        if (localSession != null) {
            return localSession;
        }
        Payload payload = requestSession.obtainSessionKey(HttpRequestHolder.getRequest());
        if (payload.isEmpty()) {
            return null;
        }
        UserSubject userSubject = sessionContext.getSession(payload.getId());
        if (userSubject == null || !userSubject.hasSession()) {
            return null;
        }
        PlatformSession session = userSubject.getPlatformSession(payload.getSessionKey());
        if (session == null) {
            return null;
        }
        long curMills = System.currentTimeMillis();
        if (session.getExpiredTimestamps() <= curMills) {
            // 过期进行刷新
            if (session.getRefreshTimestamps() <= curMills) {
                // 过期
                userSubject.removeSession(session.getSessionKey());
                this.sessionContext.putSession(payload.getId(), userSubject);
                return null;
            }
            // 刷新
            localSession = localSessionProvider.refresh(userSubject.getId(), session);
            userSubject.addSession(localSession.getPlatformSession(), false); // 只需替换相同的sessionKey用户权限
            this.sessionContext.putSession(payload.getId(), userSubject);
            localSubject.set(localSession);
            return localSession;
        }
        localSession = new LocalSession(payload.getId(), session, true);
        localSubject.set(localSession);
        return localSession;
    }

    @Override
    public boolean checkSession() {
        LocalSession localSession = this.getSession();
        if (localSession == null) {
            return false;
        }
        return localSession.isSigned();
    }

    @Override
    public void clear() throws AccountNotFoundException {
        LocalSession localSession = this.getSession();
        if (localSession == null) {
            return ;
        }
        UserSubject session = this.sessionContext.getSession(localSession.getId());
        session.removeSession(localSession.getPlatformSession().getSessionKey());
        this.sessionContext.putSession(localSession.getId(), session);
    }

    public static void close() {
        localSubject.remove();
    }

    public RequestSession getRequestSession() {
        return requestSession;
    }

    public void setRequestSession(RequestSession requestSession) {
        this.requestSession = requestSession;
    }

    public AnySessionContext<UserSubject> getSessionContext() {
        return sessionContext;
    }

    public void setSessionContext(AnySessionContext<UserSubject> sessionContext) {
        this.sessionContext = sessionContext;
    }

    public LocalSessionProvider getLocalSessionProvider() {
        return localSessionProvider;
    }

    public void setLocalSessionProvider(LocalSessionProvider localSessionProvider) {
        this.localSessionProvider = localSessionProvider;
    }

    public WebSecurityProperties getWebSecurityProperties() {
        return webSecurityProperties;
    }

    public void setWebSecurityProperties(WebSecurityProperties webSecurityProperties) {
        this.webSecurityProperties = webSecurityProperties;
    }
}
