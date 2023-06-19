package com.mtfm.security.filter;

import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.ResponseUtils;
import com.mtfm.security.SecurityCode;
import com.mtfm.security.core.HttpRequestSessionHandler;
import com.mtfm.security.core.LocalSession;
import com.mtfm.security.core.SecuritySessionContextHolder;
import com.mtfm.security.core.SessionContext;
import com.mtfm.tools.JSONUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenResolutionProcessingFilter extends AbstractTokenResolutionProcessingFilter implements MessageSourceAware {

    private HttpRequestSessionHandler httpRequestSessionHandler;
    private SessionContext<LocalSession> sessionContext;

    private MessageSourceAccessor messageSource;

    public TokenResolutionProcessingFilter(SessionContext<LocalSession> sessionContext) {
        this(new HttpRequestSessionHandler(), sessionContext);
    }

    public TokenResolutionProcessingFilter(String[] skipUrls, SessionContext<LocalSession> sessionContext) {
        this(skipUrls, new HttpRequestSessionHandler(), sessionContext);
    }

    public TokenResolutionProcessingFilter(HttpRequestSessionHandler httpRequestSessionHandler, SessionContext<LocalSession> sessionContext) {
        this(null, httpRequestSessionHandler, sessionContext);
    }

    public TokenResolutionProcessingFilter(String[] skipUrls, HttpRequestSessionHandler httpRequestSessionHandler, SessionContext<LocalSession> sessionContext) {
        super(skipUrls);
        this.httpRequestSessionHandler = httpRequestSessionHandler;
        this.sessionContext = sessionContext;
    }

    @Override
    protected Object checkSession(HttpServletRequest request) throws AccountExpiredException {
        LocalSession session = sessionContext.getSession();
        if (session == null || !session.isSigned()) {
            throw new AccountExpiredException("token had been expired");
        }
        return session;
    }

    @Override
    protected void success(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // redis 取数据
        LocalSession session = sessionContext.getSession();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken.authenticated(
                        session.getId(), session.getPlatformSession(), session.getPlatformSession().getPermissions());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }

    @Override
    protected void fail(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecuritySessionContextHolder.close();
        SecurityContextHolder.clearContext();
        RestResult<Void> failResult = RestResult.error(SecurityCode.HAS_EXPIRED.getCode(),
                this.messageSource.getMessage("UserAuthentication.tokenExpired"));
        ResponseUtils.writeObject(response, JSONUtils.toJsonString(failResult));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    public HttpRequestSessionHandler getHttpRequestSessionHandler() {
        return httpRequestSessionHandler;
    }

    public void setHttpRequestSessionHandler(HttpRequestSessionHandler httpRequestSessionHandler) {
        this.httpRequestSessionHandler = httpRequestSessionHandler;
    }

    public SessionContext<LocalSession> getSessionContext() {
        return sessionContext;
    }

    public void setSessionContext(SessionContext<LocalSession> sessionContext) {
        this.sessionContext = sessionContext;
    }
}
