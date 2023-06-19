package com.mtfm.backend_support.service.provisioning;

import com.mtfm.tools.enums.Judge;

public class UserDetailSample extends UserSample {

    private String id;

    private String identifier;

    private Judge locked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Judge getLocked() {
        return locked;
    }

    public void setLocked(Judge locked) {
        this.locked = locked;
    }

    @Override
    public boolean isEnabled() {
        return this.locked == Judge.NO;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
