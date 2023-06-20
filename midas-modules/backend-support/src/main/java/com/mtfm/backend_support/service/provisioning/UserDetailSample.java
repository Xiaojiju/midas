package com.mtfm.backend_support.service.provisioning;

import com.mtfm.core.util.validator.ValidateGroup;
import com.mtfm.tools.enums.Judge;

import javax.validation.constraints.NotNull;

public class UserDetailSample extends UserSample {

    @NotNull(groups = {ValidateGroup.Update.class}, message = "#UserInformation.nonTarget")
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

    @Override
    public boolean isAccountNonLocked() {
        return this.locked == Judge.NO;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
