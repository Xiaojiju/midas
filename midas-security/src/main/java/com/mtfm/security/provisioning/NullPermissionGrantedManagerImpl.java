package com.mtfm.security.provisioning;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class NullPermissionGrantedManagerImpl implements PermissionGrantedManager {

    @Override
    public List<GrantedAuthority> loadPermissions(String username) {
        return new ArrayList<>();
    }

}
