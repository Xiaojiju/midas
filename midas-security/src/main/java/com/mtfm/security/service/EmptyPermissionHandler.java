package com.mtfm.security.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class EmptyPermissionHandler implements GrantAuthorityHandler {

    @Override
    public List<GrantedAuthority> handle(List<GrantedAuthority> authorities) {
        return authorities;
    }
}
