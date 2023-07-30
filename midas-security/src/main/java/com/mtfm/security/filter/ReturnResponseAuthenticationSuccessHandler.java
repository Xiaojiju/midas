package com.mtfm.security.filter;

import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.ResponseUtils;
import com.mtfm.tools.JSONUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReturnResponseAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public ReturnResponseAuthenticationSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        RestResult<Object> success = RestResult.success();
        ResponseUtils.writeObject(response, JSONUtils.toJsonString(success));
    }
}
