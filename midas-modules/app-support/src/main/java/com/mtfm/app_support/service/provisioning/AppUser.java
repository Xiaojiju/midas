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
package com.mtfm.app_support.service.provisioning;

import com.mtfm.tools.enums.Judge;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户信息
 */
public class AppUser implements UserDetails, CredentialsContainer, Serializable {

    private String id;
    private String username;
    private String identifier;
    private String password;
    private LocalDateTime secretExpiredTime;
    private LocalDateTime accountExpiredTime;
    private Judge validated;
    private Judge accountLocked;
    private String additionalKey;
    private LocalDateTime usernameExpiredTime;
    private Judge secretAccess;
    private Judge loginAccess;
    private Judge thirdPart;
    private Collection<? extends GrantedAuthority> authorities;

    public AppUser() {
    }

    public AppUser(String id, String username, String identifier, String password, LocalDateTime secretExpiredTime,
                   LocalDateTime accountExpiredTime, Judge validated, Judge accountLocked, String additionalKey,
                   LocalDateTime usernameExpiredTime, Judge secretAccess, Judge loginAccess, Judge thirdPart, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.identifier = identifier;
        this.password = password;
        this.secretExpiredTime = secretExpiredTime;
        this.accountExpiredTime = accountExpiredTime;
        this.validated = validated;
        this.accountLocked = accountLocked;
        this.additionalKey = additionalKey;
        this.usernameExpiredTime = usernameExpiredTime;
        this.secretAccess = secretAccess;
        this.thirdPart = thirdPart;
        this.authorities = authorities;
        this.loginAccess = loginAccess;
    }

    public static AppUserBuilder builder(String username, String identifier) {
        return new AppUserBuilder(username, identifier);
    }

    public static AppUserBuilder builder(String id, String username, String identifier) {
        return new AppUserBuilder(id, username, identifier);
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
        if (this.accountExpiredTime == null) {
            return true;
        }
        return this.accountExpiredTime.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountLocked == Judge.NO;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (this.secretExpiredTime == null) {
            return true;
        }
        return this.secretExpiredTime.isAfter(LocalDateTime.now());
    }

    /**
     * 当前认证方式不可用
     * @return true为可以使用该认证方式，false则反之
     */
    @Override
    public boolean isEnabled() {
        if (this.usernameExpiredTime == null) {
            return true;
        }
        return this.usernameExpiredTime.isAfter(LocalDateTime.now());
    }

    public String getAdditionalKey() {
        return additionalKey;
    }

    public void setAdditionalKey(String additionalKey) {
        this.additionalKey = additionalKey;
    }

    public Judge getThirdPart() {
        return thirdPart;
    }

    public void setThirdPart(Judge thirdPart) {
        this.thirdPart = thirdPart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Judge getValidated() {
        return validated;
    }

    public void setValidated(Judge validated) {
        this.validated = validated;
    }

    public LocalDateTime getSecretExpiredTime() {
        return secretExpiredTime;
    }

    public void setSecretExpiredTime(LocalDateTime secretExpiredTime) {
        this.secretExpiredTime = secretExpiredTime;
    }

    public LocalDateTime getAccountExpiredTime() {
        return accountExpiredTime;
    }

    public void setAccountExpiredTime(LocalDateTime accountExpiredTime) {
        this.accountExpiredTime = accountExpiredTime;
    }

    public Judge getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Judge accountLocked) {
        this.accountLocked = accountLocked;
    }

    public LocalDateTime getUsernameExpiredTime() {
        return usernameExpiredTime;
    }

    public void setUsernameExpiredTime(LocalDateTime usernameExpiredTime) {
        this.usernameExpiredTime = usernameExpiredTime;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Judge getSecretAccess() {
        return secretAccess;
    }

    public void setSecretAccess(Judge secretAccess) {
        this.secretAccess = secretAccess;
    }

    public Judge getLoginAccess() {
        return loginAccess;
    }

    public void setLoginAccess(Judge loginAccess) {
        this.loginAccess = loginAccess;
    }

    public static class AppUserBuilder {

        private final String id;
        private final String username;
        private final String identifier;
        private String password;
        private LocalDateTime secretExpiredTime;
        private LocalDateTime accountExpiredTime;
        private Judge validated;
        private Judge accountLocked;
        private String additionalKey;
        private LocalDateTime usernameExpiredTime;
        private Judge secretAccess;
        private Judge loginAccess;
        private Judge thirdPart;
        private Collection<? extends GrantedAuthority> authorities;

        private AppUserBuilder(String username, String identifier) {
            this(null, username, identifier);
        }

        private AppUserBuilder(String id, String username, String identifier) {
            Assert.isTrue(StringUtils.hasText(username), "username could not be null");
            Assert.isTrue(StringUtils.hasText(identifier), "identifier could not be null");
            this.id = id;
            this.username = username;
            this.identifier = identifier;
        }

        public AppUser build() {
            if (this.validated == null) {
                this.validated = Judge.NO;
            }
            if (this.accountLocked == null) {
                this.accountLocked = Judge.NO;
            }
            if (this.secretAccess == null) {
                this.secretAccess = Judge.YES;
            }
            if (this.thirdPart == null) {
                this.thirdPart = Judge.NO;
            }
            if (this.loginAccess == null) {
                this.loginAccess = Judge.YES;
            }
            return new AppUser(this.id, this.username, this.identifier, this.password, this.secretExpiredTime,
                    this.accountExpiredTime, this.validated, this.accountLocked, this.additionalKey, this.usernameExpiredTime,
                    this.secretAccess, this.thirdPart, this.loginAccess, this.authorities);
        }

        public AppUserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public AppUserBuilder secretExpiredAt(LocalDateTime localDateTime) {
            this.secretExpiredTime = localDateTime;
            return this;
        }

        public AppUserBuilder accountExpiredAt(LocalDateTime accountExpiredTime) {
            this.accountExpiredTime = accountExpiredTime;
            return this;
        }

        public AppUserBuilder validate(boolean validated) {
            if (validated) {
                this.validated = Judge.YES;
            } else {
                this.validated = Judge.NO;
            }
            return this;
        }

        public AppUserBuilder accountLocked(boolean locked) {
            if (locked) {
                this.accountLocked = Judge.YES;
            } else {
                this.accountLocked = Judge.NO;
            }
            return this;
        }

        public AppUserBuilder withAdditional(String additionalKey) {
            this.additionalKey = additionalKey;
            return this;
        }

        public AppUserBuilder usernameExpiredAt(LocalDateTime usernameExpiredTime) {
            this.usernameExpiredTime = usernameExpiredTime;
            return this;
        }

        public AppUserBuilder usedSecret(boolean secretAccess) {
            if (secretAccess) {
                this.secretAccess = Judge.YES;
            } else {
                this.secretAccess = Judge.NO;
            }
            return this;
        }

        public AppUserBuilder thirdPart(boolean thirdPart) {
            if (thirdPart) {
                this.thirdPart = Judge.YES;
            } else {
                this.thirdPart = Judge.NO;
            }
            return this;
        }

        public AppUserBuilder putAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public AppUserBuilder usedUsername(boolean loginAccess) {
            if (loginAccess) {
                this.loginAccess = Judge.YES;
            } else {
                this.loginAccess = Judge.NO;
            }
            return this;
        }
    }
}
