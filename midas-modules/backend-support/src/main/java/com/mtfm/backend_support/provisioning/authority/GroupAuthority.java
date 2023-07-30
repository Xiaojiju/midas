package com.mtfm.backend_support.provisioning.authority;

import org.springframework.security.core.GrantedAuthority;

public class GroupAuthority implements GrantedAuthority {

    private Long id;

    private String groupName;

    public GroupAuthority() {
        this(null);
    }

    public GroupAuthority(String groupName) {
        this(null, groupName);
    }

    public GroupAuthority(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    @Override
    public String getAuthority() {
        return this.groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
