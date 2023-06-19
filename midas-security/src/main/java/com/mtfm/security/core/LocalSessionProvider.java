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
import com.mtfm.security.Client;
import com.mtfm.security.SecurityConstants;
import com.mtfm.security.config.WebAuthProperties;
import com.mtfm.security.context.Auth0TokenProvider;
import com.mtfm.security.context.TokenProvider;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 当前会话生成器 {@link LocalSession}
 * @author 一块小饼干
 * @since 1.0.0
 */
public class LocalSessionProvider {

    private WebAuthProperties webAuthProperties;
    private TokenProvider tokenProvider;

    public LocalSessionProvider(WebAuthProperties webAuthProperties) {
        this(webAuthProperties, new Auth0TokenProvider());
    }

    public LocalSessionProvider(WebAuthProperties webAuthProperties, TokenProvider tokenProvider) {
        this.webAuthProperties = webAuthProperties;
        this.tokenProvider = tokenProvider;
    }

    public LocalSession provide(HttpServletRequest request, Client client, UserDetails userDetails) {
        Map<String, String> map = new HashMap<>();
        map.put(SecurityConstants.SESSION_UID_CLAIM, userDetails.getUsername());
        String ip = HttpRequestHolder.getIpAddress(request);
        LocalSession.LocalSessionBuilder localSessionBuilder = LocalSession.LocalSessionBuilder
                .withId(userDetails.getUsername())
                .withSessionKey(tokenProvider.make(map),
                        webAuthProperties.getExpiredTimestamp(),
                        webAuthProperties.getRefreshTimestamp())
                .withPlatform(ip, "", "", client.getName());
        return localSessionBuilder.build();
    }

    public LocalSession refresh(LocalSession localSession) {
        return refresh(localSession.getId(), localSession.getPlatformSession());
    }

    public LocalSession refresh(String id, PlatformSession platformSession) {
        LocalSession.LocalSessionBuilder localSessionBuilder = LocalSession.LocalSessionBuilder
                .withId(id)
                .withSessionKey(platformSession.getSessionKey(),
                        webAuthProperties.getExpiredTimestamp(),
                        webAuthProperties.getRefreshTimestamp())
                .requestSession(platformSession.getSessionRequest());
        return localSessionBuilder.build();
    }

    public WebAuthProperties getWebAuthProperties() {
        return webAuthProperties;
    }

    public void setWebAuthProperties(WebAuthProperties webAuthProperties) {
        this.webAuthProperties = webAuthProperties;
    }

    public TokenProvider getTokenProvider() {
        return tokenProvider;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
