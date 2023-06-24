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
package com.mtfm.backend_support.service.provisioning;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtfm.core.util.TimeConstants;
import com.mtfm.core.util.validator.ValidateGroup;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 */
public class UserSample implements UserDetails, CredentialsContainer, Serializable {

    @NotNull(groups = {ValidateGroup.Create.class}, message = "#UserInformation.username")
    private String username;

    private String password;

    @JsonFormat(pattern = TimeConstants.Y_M_D_H_M_S)
    private LocalDateTime expiredTime;

    @Size(groups = {ValidateGroup.Create.class}, min = 1, max = 10, message = "#UserInformation.role")
    private List<RoleGrantedAuthority> roles;

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (this.expiredTime != null) {
            return this.expiredTime.isAfter(LocalDateTime.now());
        }
        // 默认
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public List<RoleGrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleGrantedAuthority> roles) {
        this.roles = roles;
    }
}
