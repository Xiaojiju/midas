package com.mtfm.security.filter;

import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.ResponseUtils;
import com.mtfm.tools.JSONUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonBasedLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        ResponseUtils.writeObject(response, JSONUtils.toJsonString(RestResult.success()));
    }
}
