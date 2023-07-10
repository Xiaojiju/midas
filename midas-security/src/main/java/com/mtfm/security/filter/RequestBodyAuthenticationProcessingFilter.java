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
package com.mtfm.security.filter;

import com.mtfm.core.util.tools.IOUtils;
import com.mtfm.security.Payload;
import com.mtfm.security.core.SecuritySessionContextHolder;
import com.mtfm.tools.JSONUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * login filter
 * 普通账号密码登陆
 * @author 一块小饼干
 * @since 1.0.0
 */
public class RequestBodyAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 请求api
     */
    private static final String LOGIN_URL = "/solar/api/v1/login";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(LOGIN_URL, "POST");

    public RequestBodyAuthenticationProcessingFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    /**
     * 重写尝试身份认证方法，在body中读取认证信息
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException, IOException {
        Payload payload = this.readRequestBody(request);
        if (payload == null) {
            payload = new Payload();
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        payload.getUsername(), payload.getPassword());
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
        SecuritySessionContextHolder.close();
        super.unsuccessfulAuthentication(request, response, failed);
    }

    /**
     * 读取body中认证信息
     * 该方法需要被优化，否则在恶意攻击时，传入大长度数据进来，会导致io持续读取，造成吞吐下降
     * 暂时使用该实现，在下个版本优化修复
     * @since 1.0.0
     * @return 返回读取到的body认证信息
     */
    private Payload readRequestBody(HttpServletRequest request) throws IOException {
        String body = IOUtils.read(request.getInputStream());
        return JSONUtils.parse(body, Payload.class);
    }

}
