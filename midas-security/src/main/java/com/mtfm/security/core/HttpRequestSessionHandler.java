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

import com.mtfm.security.SecurityConstants;
import com.mtfm.security.context.Auth0TokenProvider;
import com.mtfm.security.context.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 认证请求会话处理
 * @author 一块小饼干
 * @since 1.0.0
 */
public class HttpRequestSessionHandler implements RequestSession {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestSessionHandler.class);

    private static final String SESSION_HEADER = "Authentication";

    private String header;
    private TokenProvider tokenProvider;

    public HttpRequestSessionHandler() {
        this(SESSION_HEADER);
    }

    public HttpRequestSessionHandler(String header) {
        this(header, new Auth0TokenProvider());
    }

    public HttpRequestSessionHandler(String header, TokenProvider tokenProvider) {
        if (StringUtils.hasText(header)) {
            this.header = header;
        } else {
            this.header = SESSION_HEADER;
        }
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Payload obtainSessionKey(HttpServletRequest request) {
        try {
            String sessionKey = request.getHeader(this.header);
            if (StringUtils.hasText(header)) {
                Map<String, String> parse = tokenProvider.parse(sessionKey);
                if (CollectionUtils.isEmpty(parse)) {
                    return RequestPayload.unCarryToken(null, null);
                }
                return RequestPayload.carryToken(parse.get(SecurityConstants.SESSION_UID_CLAIM), sessionKey);
            }
            return RequestPayload.unCarryToken(null, null);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("parse request header error, could not found authentication token");
            }
            return RequestPayload.unCarryToken(null, null);
        }
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public TokenProvider getTokenProvider() {
        return tokenProvider;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
