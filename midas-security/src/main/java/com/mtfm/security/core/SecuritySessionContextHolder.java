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
import com.mtfm.security.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.Assert;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户会话操作类
 * 其中在{@link #putSession(Authentication)}方法和{@link #getSession()}方法中，包含获取本地时间的
 *
 *         long curMills = System.currentTimeMillis();
 *
 * 这段代码可能在一些场景中会出现不是预期效果，比如在国外服务器上，但是需要保持与国内的时间统一，则该代码就会导致时间不精准的问题；
 * 如果遇到该类似场景下，可以使用以后代码替换：
 *
 *         long curMills = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")); // 设置东八区时区
 *
 * 或者设置机器的时区也可以让System.currentTimeMills()获取到期望的时间戳；
 * @author 一块小饼干
 * @since 1.0.0
 */
public class SecuritySessionContextHolder implements SessionContext<Authentication> {

    /**
     * 15分钟
     */
    private static final long EXPIRED_TIMESTAMP = 900L * 1000L;
    /**
     * 7天
     */
    private static final long REFRESH_TIMESTAMP = 604800L * 1000L;
    private static final ThreadLocal<Authentication> localSubject = new ThreadLocal<>();
    private RequestSession requestSession;
    private AnySessionContext<UserSubject> sessionContext;
    private LocalSessionProvider localSessionProvider;
    private final long expiredTimestamp;
    private final long refreshTimestamp;
    private final boolean enableRefresh;

    public SecuritySessionContextHolder(AnySessionContext<UserSubject> sessionContext) {
        this(new HttpRequestSessionHandler(), sessionContext, new LocalSessionProvider());
    }

    public SecuritySessionContextHolder(RequestSession requestSession,
                                        AnySessionContext<UserSubject> sessionContext,
                                        LocalSessionProvider localSessionProvider) {
        this(requestSession, sessionContext, localSessionProvider, EXPIRED_TIMESTAMP, REFRESH_TIMESTAMP, true);
    }

    public SecuritySessionContextHolder(long expiredTimestamp, long refreshTimestamp, boolean enableRefresh) {
        this(new HttpRequestSessionHandler(), new AnySessionContextHandler(), new LocalSessionProvider(),
                expiredTimestamp, refreshTimestamp, enableRefresh);
    }

    public SecuritySessionContextHolder(RequestSession requestSession, AnySessionContext<UserSubject> sessionContext,
                                        LocalSessionProvider localSessionProvider, long expiredTimestamp,
                                        long refreshTimestamp, boolean enableRefresh) {
        this.requestSession = requestSession;
        this.sessionContext = sessionContext;
        this.localSessionProvider = localSessionProvider;
        this.expiredTimestamp = expiredTimestamp;
        this.refreshTimestamp = refreshTimestamp;
        this.enableRefresh = enableRefresh;
    }

    /**
     * 更新LocalSession的信息
     * @param authentication 用户认证后的令牌
     */
    @Override
    public void putSession(Authentication authentication) {
        if (authentication == null) {
            throw new NullPointerException("could be set null to local session context");
        }
        Assert.isInstanceOf(LocalSessionToken.class, authentication, "only supports LocalSessionToken");
        LocalSessionToken localSessionToken = (LocalSessionToken) authentication;
        // 不能使用异步
        HttpServletRequest request = HttpRequestHolder.getRequest();
        String ip = null;
        if (request != null) {
            ip = HttpRequestHolder.getIpAddress(request);
        }
        long curMills = System.currentTimeMillis();
        UserSubject userSubject = UserSubject.builder().expiredAt(this.expiredTimestamp + curMills)
                .refreshIn(this.refreshTimestamp + curMills)
                .signAt(curMills)
                .withIp(ip)
                .withPermissions(AuthorityUtils.authorityListToSet(localSessionToken.getAuthorities()))
                .build();
        String id = getPrincipal(localSessionToken);
        localSessionToken.setDetails(userSubject);
        this.sessionContext.putSession(id, userSubject);
    }

    /**
     * 获取当前认证的会话，如果当前会话已经过期，则根据设定的刷新策略{@link com.mtfm.security.config.WebAutoSecurityConfiguration}进
     * 行刷新当前会话
     */
    @Override
    public Authentication getSession() {
        Authentication authentication = localSubject.get();
        if (authentication != null) {
            return authentication;
        }
        Payload payload = this.requestSession.obtainSessionKey(HttpRequestHolder.getRequest());
        if (payload.isEmpty()) {
            authentication = new LocalSessionToken();
        }
        UserSubject userSubject = this.sessionContext.getSession(payload.getId());
        if (userSubject == null) {
            authentication = new LocalSessionToken();
        }
        long curMills = System.currentTimeMillis();
        if (userSubject != null && userSubject.getExpiredTimestamps() <= curMills) {
            // 过期进行刷新
            if (userSubject.getRefreshTimestamps() <= curMills) {
                // 过期
                this.sessionContext.clear(payload.getId());
                return null;
            }
            // 刷新
            if (enableRefresh) {
                userSubject.setExpiredTimestamps(curMills + this.expiredTimestamp);
                userSubject.setRefreshTimestamps(curMills + refreshTimestamp);
                this.sessionContext.putSession(payload.getId(), userSubject);
            }
            authentication = new LocalSessionToken(createAuthorityList(userSubject.getPermissions()), payload.getId(),
                    payload.getSessionKey());
        }
        localSubject.set(authentication);
        return authentication;
    }

    @Override
    public boolean checkSession() {
        Authentication authentication = this.getSession();
        if (authentication == null) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @Override
    public void clear() throws AccountNotFoundException {
        Authentication authentication = this.getSession();
        if (authentication == null) {
            return ;
        }
        String id = getPrincipal(authentication);
        UserSubject session = this.sessionContext.getSession(id);
    }

    public static void close() {
        localSubject.remove();
    }

    private Set<GrantPermission> createAuthorityList(Set<String> permissions) {
        Assert.notNull(permissions, "userAuthorities cannot be null");
        Set<GrantPermission> set = new HashSet<>(permissions.size());
        for (String permission : permissions) {
            set.add(new GrantPermission(permission));
        }
        return set;
    }

    private String getPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            principal = authentication.getName();
            if (principal == null) {
                throw new NullPointerException("authentication do not obtain principal");
            }
        }
        String id;
        if (principal instanceof AppUser) {
            id = ((AppUser) principal).getId();
        } else {
            id = principal.toString();
        }
        return id;
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
}
