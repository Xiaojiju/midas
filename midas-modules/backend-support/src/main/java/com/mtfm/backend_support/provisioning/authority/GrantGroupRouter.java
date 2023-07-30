package com.mtfm.backend_support.provisioning.authority;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public class GrantGroupRouter implements Serializable {

    private String groupName;

    private List<GrantedAuthority> routers;

    public GrantGroupRouter() {
    }

    public GrantGroupRouter(String groupName, List<GrantedAuthority> routers) {
        this.groupName = groupName;
        this.routers = routers;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<GrantedAuthority> getRouters() {
        return routers;
    }

    public void setRouters(List<GrantedAuthority> routers) {
        this.routers = routers;
    }
}
