package com.mtfm.security.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class NullGrantAuthorityServiceImpl implements GrantAuthorityService {

    @Override
    public List<GrantedAuthority> loadPermissions(String uniqueId) {
        return new ArrayList<>();
    }

}
