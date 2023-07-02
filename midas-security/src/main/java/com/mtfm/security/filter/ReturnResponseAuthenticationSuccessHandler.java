package com.mtfm.security.filter;

import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.ResponseUtils;
import com.mtfm.security.authentication.SecurityToken;
import com.mtfm.security.core.*;
import com.mtfm.tools.JSONUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReturnResponseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private SessionContext<Authentication> securitySessionContextHolder;
    private LocalSessionProvider localSessionProvider;

    public ReturnResponseAuthenticationSuccessHandler() {
        this(new SecuritySessionContextHolder(), new LocalSessionProvider());
    }

    public ReturnResponseAuthenticationSuccessHandler(SessionContext<Authentication> securitySessionContextHolder,
                                                      LocalSessionProvider localSessionProvider) {
        this.securitySessionContextHolder = securitySessionContextHolder;
        this.localSessionProvider = localSessionProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        LocalSessionToken localSessionToken = localSessionProvider.provide(authentication);
        securitySessionContextHolder.putSession(localSessionToken);
        UserSubject userSubject = (UserSubject) localSessionToken.getDetails();
        SecurityToken securityToken = SecurityToken.SecurityTokenBuilder.withAccessToken((String) localSessionToken.getCredentials())
                        .setSignAccessTimestamp(userSubject.getSignTimestamps()).build();
        ResponseUtils.writeObject(response, JSONUtils.toJsonString(RestResult.success(securityToken)));
    }

    public SessionContext<Authentication> getSecuritySessionContextHolder() {
        return securitySessionContextHolder;
    }

    public void setSecuritySessionContextHolder(SessionContext<Authentication> securitySessionContextHolder) {
        this.securitySessionContextHolder = securitySessionContextHolder;
    }

    public LocalSessionProvider getLocalSessionProvider() {
        return localSessionProvider;
    }

    public void setLocalSessionProvider(LocalSessionProvider localSessionProvider) {
        this.localSessionProvider = localSessionProvider;
    }
}
