package com.mtfm.security.filter;

import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.ResponseUtils;
import com.mtfm.security.SecurityCode;
import com.mtfm.security.context.SolarMessageSource;
import com.mtfm.tools.JSONUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseBodyExpiredStrategy implements SessionInformationExpiredStrategy, MessageSourceAware {

    private MessageSourceAccessor messages = SolarMessageSource.getAccessor();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        RestResult<Object> error = RestResult.error(SecurityCode.HAS_EXPIRED.getCode(),
                this.messages.getMessage("UserTemplatePreAuthenticationChecks.expired"));
        ResponseUtils.writeObject(response, JSONUtils.toJsonString(error));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
