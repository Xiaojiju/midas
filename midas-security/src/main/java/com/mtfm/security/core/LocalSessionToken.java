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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 当前请求会话
 * 该类为会话中的第二层，第一层为{@link UserSubject}，为当前的认证平台的会话信息
 * @author 一块小饼干
 * @since 1.0.0
 */
public class LocalSessionToken extends AbstractAuthenticationToken {

    private static final Logger logger = LoggerFactory.getLogger(LocalSessionToken.class);
    /**
     * 当前用户id
     */
    private final Object principal;
    /**
     * 当前sessionKey
     */
    private final Object credentials;

    public LocalSessionToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public LocalSessionToken() {
        super(null);
        this.principal = "";
        this.credentials = "";
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        // 不进行操作
        if (logger.isDebugEnabled()) {
            logger.debug("could set trust to this token, it can not be changed");
        }
    }
}
