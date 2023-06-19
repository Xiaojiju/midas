package com.mtfm.security.filter;

import com.mtfm.core.context.response.RestResult;
import com.mtfm.security.Client;
import com.mtfm.core.util.ResponseUtils;
import com.mtfm.security.UserTemplate;
import com.mtfm.security.authentication.SecurityToken;
import com.mtfm.security.core.LocalSession;
import com.mtfm.security.core.LocalSessionProvider;
import com.mtfm.security.core.PlatformSession;
import com.mtfm.security.core.SessionContext;
import com.mtfm.tools.JSONUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReturnResponseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private SessionContext<LocalSession> securitySessionContextHolder;
    private LocalSessionProvider localSessionProvider;

    public ReturnResponseAuthenticationSuccessHandler(SessionContext<LocalSession> securitySessionContextHolder, LocalSessionProvider localSessionProvider) {
        this.securitySessionContextHolder = securitySessionContextHolder;
        this.localSessionProvider = localSessionProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        LocalSession localSession = localSessionProvider
                .provide(request, Client.WEB, (UserTemplate) authentication.getPrincipal());
        securitySessionContextHolder.putSession(localSession);
        PlatformSession session = localSession.getPlatformSession();
        SecurityToken securityToken = SecurityToken.SecurityTokenBuilder.withAccessToken(session.getSessionKey())
                        .setSignAccessTimestamp(session.getSignTimestamps()).build();
        ResponseUtils.writeObject(response, JSONUtils.toJsonString(RestResult.success(securityToken)));
    }

    public SessionContext<LocalSession> getSecuritySessionContextHolder() {
        return securitySessionContextHolder;
    }

    public void setSecuritySessionContextHolder(SessionContext<LocalSession> securitySessionContextHolder) {
        this.securitySessionContextHolder = securitySessionContextHolder;
    }

    public LocalSessionProvider getLocalSessionProvider() {
        return localSessionProvider;
    }

    public void setLocalSessionProvider(LocalSessionProvider localSessionProvider) {
        this.localSessionProvider = localSessionProvider;
    }
}
