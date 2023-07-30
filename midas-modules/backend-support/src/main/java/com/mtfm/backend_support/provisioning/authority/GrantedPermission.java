package com.mtfm.backend_support.provisioning.authority;

import org.springframework.security.core.GrantedAuthority;

public class GrantedPermission implements GrantedAuthority {

    private String authority;

    public GrantedPermission() {
    }

    public GrantedPermission(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
