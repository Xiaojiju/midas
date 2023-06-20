package com.mtfm.backend_support.service.provisioning;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

public class RoleGrantedAuthority implements GrantedAuthority {

    private String id;

    private String roleName;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
