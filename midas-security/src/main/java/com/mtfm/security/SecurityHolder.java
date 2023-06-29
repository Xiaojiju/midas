package com.mtfm.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityHolder {

    public static Object getPrincipal() {
        return getAuthentication().getPrincipal();
    }

    public static Object getDetails() {
        return getAuthentication().getDetails();
    }

    public static Object getCredentials() {
        return getAuthentication().getCredentials();
    }

    public static boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    public static SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

    public static Authentication getAuthentication() {
        return getContext().getAuthentication();
    }
}
