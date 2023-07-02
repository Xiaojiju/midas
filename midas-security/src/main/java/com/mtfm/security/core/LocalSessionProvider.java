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

import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityConstants;
import com.mtfm.security.context.Auth0TokenProvider;
import com.mtfm.security.context.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 当前会话生成器 {@link LocalSessionToken}
 * @author 一块小饼干
 * @since 1.0.0
 */
public class LocalSessionProvider {
    private TokenProvider tokenProvider;

    public LocalSessionProvider() {
        this(new Auth0TokenProvider());
    }

    public LocalSessionProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public LocalSessionToken provide(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Assert.isInstanceOf(AppUser.class, principal, "only support AppUser.class");
        AppUser appUser = (AppUser) principal;
        Map<String, String> map = new HashMap<>();
        map.put(SecurityConstants.SESSION_UID_CLAIM, appUser.getId());
        map.put(SecurityConstants.SESSION_USERNAME, appUser.getUsername());
        String sessionKey = tokenProvider.make(map);
        return new LocalSessionToken(appUser.getAuthorities(), appUser.getId(), sessionKey);
    }

    protected TokenProvider getTokenProvider() {
        return tokenProvider;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
