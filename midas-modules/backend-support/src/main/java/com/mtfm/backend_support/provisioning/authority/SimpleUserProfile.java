package com.mtfm.backend_support.provisioning.authority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public class SimpleUserProfile extends SimpleUser implements Serializable, CredentialsContainer {

    @JsonIgnore
    private String password;

    private List<GrantedAuthority> groups;

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getGroups() {
        return groups;
    }

    public void setGroups(List<GrantedAuthority> groups) {
        this.groups = groups;
    }
}
