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
package com.mtfm.wechat_mp.filter;

import com.mtfm.core.util.tools.IOUtils;
import com.mtfm.tools.JSONUtils;
import com.mtfm.wechat_mp.authentication.MiniProgramAuthenticationToken;
import com.mtfm.wechat_mp.authentication.MpUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 微信小程序登陆
 */
public class MiniProgramAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 请求api
     */
    private static final String LOGIN_URL = "/solar/api/v1/login/mp";
    private static final String JS_CODE = "jsCode";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(LOGIN_URL, "POST");

    public MiniProgramAuthenticationProcessingFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public MiniProgramAuthenticationProcessingFilter(AuthenticationSuccessHandler successHandler,
                                                     AuthenticationFailureHandler failureHandler) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        super.setAuthenticationFailureHandler(failureHandler);
        super.setAuthenticationSuccessHandler(successHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        MpUser.UserInfo userInfo = getCode(request);
        if (userInfo == null) {
            return UsernamePasswordAuthenticationToken.unauthenticated(null, null);
        }
        MiniProgramAuthenticationToken unauthenticated =
                MiniProgramAuthenticationToken.unauthenticated(null, userInfo.getMpUser(), userInfo.getJsCode());
        return this.getAuthenticationManager().authenticate(unauthenticated);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    private MpUser.UserInfo getCode(HttpServletRequest request) throws IOException {
        String body = IOUtils.read(request.getInputStream());
        return JSONUtils.from(body, MpUser.UserInfo.class);
    }
}
