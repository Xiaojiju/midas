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

import com.mtfm.tools.enums.Judge;
import org.springframework.security.core.CredentialsContainer;

import java.time.LocalDateTime;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 用户业务数据传输实体
 */
public class UserDto implements CredentialsContainer {

    private String uniqueId;
    private String currentReferenceKey;
    private String secretKey;
    private LocalDateTime accountExpiredTime;
    private Judge accountLocked;
    private String currentMethod;
    private LocalDateTime currentMethodExpiredTime;
    private Judge currentMethodLocked;
    private LocalDateTime credentialsExpiredTime;

    @Override
    public void eraseCredentials() {
        this.secretKey = null;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCurrentReferenceKey() {
        return currentReferenceKey;
    }

    public void setCurrentReferenceKey(String currentReferenceKey) {
        this.currentReferenceKey = currentReferenceKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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

    public String getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentMethod(String currentMethod) {
        this.currentMethod = currentMethod;
    }

    public LocalDateTime getCurrentMethodExpiredTime() {
        return currentMethodExpiredTime;
    }

    public void setCurrentMethodExpiredTime(LocalDateTime currentMethodExpiredTime) {
        this.currentMethodExpiredTime = currentMethodExpiredTime;
    }

    public Judge getCurrentMethodLocked() {
        return currentMethodLocked;
    }

    public void setCurrentMethodLocked(Judge currentMethodLocked) {
        this.currentMethodLocked = currentMethodLocked;
    }

    public LocalDateTime getCredentialsExpiredTime() {
        return credentialsExpiredTime;
    }

    public void setCredentialsExpiredTime(LocalDateTime credentialsExpiredTime) {
        this.credentialsExpiredTime = credentialsExpiredTime;
    }
}
