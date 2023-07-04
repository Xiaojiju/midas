package com.mtfm.security.filter;

import com.mtfm.core.ResultCode;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.ResponseUtils;
import com.mtfm.security.SecurityCode;
import com.mtfm.security.context.SolarMessageSource;
import com.mtfm.tools.JSONUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReturnResponseAuthenticationFailHandler implements AuthenticationFailureHandler, MessageSourceAware {

    private MessageSourceAccessor messages;

    public ReturnResponseAuthenticationFailHandler() {
        this.messages = SolarMessageSource.getAccessor();
    }

    public ReturnResponseAuthenticationFailHandler(MessageSource messages) {
        setMessageSource(messages);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        RestResult<Void> failResult;
        if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            failResult = RestResult.error(SecurityCode.BAD_CREDENTIAL.getCode(),
                    this.messages.getMessage("ReturnResponseAuthenticationFailHandler.bad_credential",
                            "bad credential"));
        } else if (exception instanceof InternalAuthenticationServiceException) {
            failResult = RestResult.error(SecurityCode.NO_AUTHORITIES.getCode(),
                    this.messages.getMessage("ReturnResponseAuthenticationFailHandler.no_authorities",
                            "no authorities"));
        } else if (exception instanceof AccountExpiredException) {
            failResult = RestResult.error(SecurityCode.HAS_EXPIRED.getCode(),
                    this.messages.getMessage("ReturnResponseAuthenticationFailHandler.expired",
                            "User has expired or current method has expired"));
        } else if (exception instanceof LockedException) {
            failResult = RestResult.error(SecurityCode.LOCKED.getCode(),
                    this.messages.getMessage("ReturnResponseAuthenticationFailHandler.locked",
                            "User has been locked"));
        } else if (exception instanceof CredentialsExpiredException) {
            failResult = RestResult.error(SecurityCode.PASSWORD_EXPIRED.getCode(),
                    this.messages.getMessage("UserTemplatePreAuthenticationChecks.passwordExpired",
                            "password had expired"));
        } else {
            failResult = RestResult.complete(ResultCode.FORBIDDEN);
        }
        ResponseUtils.writeObject(response, JSONUtils.toJsonString(failResult));
    }
}
