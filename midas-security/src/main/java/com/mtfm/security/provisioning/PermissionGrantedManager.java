package com.mtfm.security.provisioning;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface PermissionGrantedManager {

    List<GrantedAuthority> loadPermissions(String username);

}
