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

import com.mtfm.core.util.validator.ValidateGroup;
import com.mtfm.tools.enums.Judge;

import javax.validation.constraints.NotNull;

/**
 * @author 一块小饼干
 * @since 1.0.0
 */
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
