/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.security;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 用户认证信息，认证后不允许更改
 * @author 一块小饼干
 * @since 1.0.0
 */
public class UserTemplate extends User implements MultiAuthenticateMethodUserDetails, CredentialsContainer {

    private final String currentMethod;

    private final boolean isCurrentMethodNonExpired;

    private final boolean isCurrentMethodNonLocked;

    public UserTemplate(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, authorities, "none", true, true);
    }

    public UserTemplate(String username, String password, Collection<? extends GrantedAuthority> authorities,
                        String currentMethod, boolean isCurrentMethodNonExpired, boolean isCurrentMethodNonLocked) {
        this(username, password, true, true, true, true,
                authorities, currentMethod, isCurrentMethodNonExpired, isCurrentMethodNonLocked);
    }

    public UserTemplate(User user, String currentMethod, boolean isCurrentMethodNonExpired,
                        boolean isCurrentMethodNonLocked) {
        this(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
                user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities(),
                currentMethod, isCurrentMethodNonExpired, isCurrentMethodNonLocked);
    }

    public UserTemplate(String username, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities, String currentMethod,
                        boolean isCurrentMethodNonExpired, boolean isCurrentMethodNonLocked) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.currentMethod = currentMethod;
        this.isCurrentMethodNonExpired = isCurrentMethodNonExpired;
        this.isCurrentMethodNonLocked = isCurrentMethodNonLocked;
    }

    @Override
    public String getCurrentMethod() {
        return this.currentMethod;
    }

    @Override
    public boolean isCurrentMethodNonExpired() {
        return this.isCurrentMethodNonExpired;
    }

    @Override
    public boolean isCurrentMethodNonLocked() {
        return this.isCurrentMethodNonLocked;
    }

}
