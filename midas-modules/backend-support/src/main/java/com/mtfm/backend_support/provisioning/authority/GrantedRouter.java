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
package com.mtfm.backend_support.provisioning.authority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtfm.backend_support.MenuLevel;
import com.mtfm.backend_support.entity.SolarRouter;
import com.mtfm.core.util.Linkable;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 菜单
 */
public class GrantedRouter implements Serializable, Linkable<GrantedRouter>, GrantedAuthority {

    private String key;

    private String parent;

    private String routerName;

    private String routerLink;

    private String componentAddress;

    private MenuLevel menuLevel;

    @JsonIgnore
    private String authorityCode;

    private int height;

    private List<GrantedRouter> nodes;

    private String icon;

    public static GrantedRouter buildParentNode() {
        GrantedRouter parent = new GrantedRouter();
        parent.setKey(SolarRouter.DEFAULT_PARENT_ID);
        return parent;
    }

    @Override
    public String getAuthority() {
        return this.authorityCode;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    @Override
    public List<GrantedRouter> getNodes() {
        return this.nodes;
    }

    @Override
    public void setNodes(List<GrantedRouter> nodes) {
        this.nodes = nodes;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public String getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public String getComponentAddress() {
        return componentAddress;
    }

    public void setComponentAddress(String componentAddress) {
        this.componentAddress = componentAddress;
    }

    public MenuLevel getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(MenuLevel menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }
}
