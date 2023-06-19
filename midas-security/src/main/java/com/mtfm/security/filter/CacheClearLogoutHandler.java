package com.mtfm.security.filter;

import com.mtfm.security.core.LocalSession;
import com.mtfm.security.core.SessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CacheClearLogoutHandler implements LogoutHandler {

    private final Logger logger = LoggerFactory.getLogger(CacheClearLogoutHandler.class);
    private SessionContext<LocalSession> sessionContext;

    public CacheClearLogoutHandler(SessionContext<LocalSession> sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            sessionContext.clear();
        } catch (AccountNotFoundException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("session could not been found");
            }
        }
    }

    public SessionContext<LocalSession> getSessionContext() {
        return sessionContext;
    }

    public void setSessionContext(SessionContext<LocalSession> sessionContext) {
        this.sessionContext = sessionContext;
    }
}
