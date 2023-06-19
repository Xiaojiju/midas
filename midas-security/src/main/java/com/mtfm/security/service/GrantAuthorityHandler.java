package com.mtfm.security.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface GrantAuthorityHandler {

    List<GrantedAuthority> handle(List<GrantedAuthority> authorities);

}
