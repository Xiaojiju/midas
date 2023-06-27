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
package com.mtfm.wechat_mp.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 小程序授权令牌
 */
public class MiniProgramAuthenticationToken extends AbstractAuthenticationToken {

    private static final Logger logger = LoggerFactory.getLogger(MiniProgramAuthenticationToken.class);

    private final Object principal;

    private final Object details;
    private final Object credentials;

    public MiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object details, Object credentials) {
        super(authorities);
        this.principal = principal;
        this.details = details;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public MiniProgramAuthenticationToken(Object principal, Object details, Object credentials) {
        super(null);
        this.principal = principal;
        this.details = details;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public static MiniProgramAuthenticationToken unauthenticated(Object principal, Object details, Object credentials) {
        return new MiniProgramAuthenticationToken(principal, details, credentials);
    }

    public static MiniProgramAuthenticationToken authenticated(Object principal, Object details, Object credentials,
                                                               Collection<? extends GrantedAuthority> authorities) {
        return new MiniProgramAuthenticationToken(authorities, principal, details, credentials);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getDetails() {
        return this.c;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        Assert.isTrue(!authenticated, "Cannot set this token to trusted - " +
                "use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }
}
