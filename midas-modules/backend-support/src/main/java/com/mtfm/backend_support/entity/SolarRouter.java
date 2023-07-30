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
package com.mtfm.backend_support.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mtfm.backend_support.MenuLevel;
import com.mtfm.datasource.BaseModel;
import com.mtfm.tools.enums.Judge;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 菜单
 * 默认的parentId为0，由于在组装{@link com.mtfm.core.util.NodeTree}的时候，需要找到最顶级的id
 */
@TableName("solar_router")
public class SolarRouter extends BaseModel<SolarRouter> implements Serializable {

    public static final String DEFAULT_PARENT_ID = "0";

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("router_name")
    private String routerName;

    @TableField("router_link")
    private String routerLink;

    @TableField("component_address")
    private String componentAddress;

    @TableField("menu_level")
    private MenuLevel menuLevel;

    @TableField("sort")
    private Integer sort;

    @TableField("authority_code")
    private String authorityCode;

    @TableField("icon")
    private String icon;

    @TableField("group_id")
    private String groupId;

    @TableField("parent_id")
    private String parentId;

    @TableField("hidden")
    private Judge hidden;

    public SolarRouter() {
    }

    public SolarRouter(String id, String routerName, String routerLink, String componentAddress,
                       MenuLevel menuLevel, Integer sort, String authorityCode, String icon, String groupId, String parentId,
                       Judge hidden) {
        this.id = id;
        this.routerName = routerName;
        this.routerLink = routerLink;
        this.componentAddress = componentAddress;
        this.menuLevel = menuLevel;
        this.sort = sort;
        this.authorityCode = authorityCode;
        this.icon = icon;
        this.groupId = groupId;
        this.parentId = parentId;
        this.hidden = hidden;
    }

    public SolarMenuBuilder withRouterName(String routerName) {
        return SolarMenuBuilder.builder().withRouterName(routerName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Judge getHidden() {
        return hidden;
    }

    public void setHidden(Judge hidden) {
        this.hidden = hidden;
    }

    public static class SolarMenuBuilder {

        private String id;
        private String menuName;
        private String routerName;
        private String routerLink;
        private String componentAddress;
        private MenuLevel menuLevel;
        private Integer sort;
        private String authorityCode;
        private String icon;
        private String groupId;
        private String parentId;
        private Judge hidden;

        private SolarMenuBuilder() {}

        public SolarMenuBuilder(SolarRouter solarMenu) {
            this.id = solarMenu.id;
            this.routerName = solarMenu.routerName;
            this.routerLink = solarMenu.routerLink;
            this.componentAddress = solarMenu.componentAddress;
            this.menuLevel = solarMenu.menuLevel;
            this.sort = solarMenu.sort;
            this.authorityCode = solarMenu.authorityCode;
            this.icon = solarMenu.icon;
            this.groupId = solarMenu.groupId;
            this.parentId = solarMenu.parentId;
        }

        public static SolarMenuBuilder builder() {
            return new SolarMenuBuilder();
        }

        public SolarMenuBuilder withRouterName(String routerName) {
            this.routerName = routerName;
            return this;
        }

        public SolarMenuBuilder withRouterLink(String routerLink) {
            this.routerLink = routerLink;
            return this;
        }

        public SolarMenuBuilder withRouter(String routerName, String routerLink) {
            this.routerLink = routerLink;
            this.routerName = routerName;
            return this;
        }

        public SolarMenuBuilder withComponent(String componentAddress) {
            this.componentAddress = componentAddress;
            return this;
        }

        public SolarMenuBuilder withMenuLevel(MenuLevel level) {
            this.menuLevel = level;
            return this;
        }

        public SolarMenuBuilder withMenuLevel(int level) {
            MenuLevel selected = MenuLevel.selectLevel(level);
            Assert.notNull(selected, "could not find MenuLevel by " + level);
            return withMenuLevel(selected);
        }

        public SolarMenuBuilder sort(int sort) {
            this.sort = sort;
            return this;
        }

        public SolarMenuBuilder withPermission(String authorityCode) {
            this.authorityCode = authorityCode;
            return this;
        }

        public SolarMenuBuilder withPermission(GrantedAuthority grantedAuthority) {
            this.authorityCode = grantedAuthority.getAuthority();
            return this;
        }

        public SolarMenuBuilder addIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public SolarMenuBuilder withGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public SolarMenuBuilder withParent(String parentId) {
            this.parentId = parentId;
            return this;
        }

        public SolarMenuBuilder hidden() {
            this.hidden = Judge.YES;
            return this;
        }

        public SolarMenuBuilder show() {
            this.hidden = Judge.NO;
            return this;
        }

        public SolarRouter build() {
            if (this.hidden == null) {
                this.hidden = Judge.NO;
            }
            Assert.isTrue(StringUtils.hasText(this.menuName), "menu name could not be null");
            Assert.notNull(this.menuLevel, "menu level could not be null");
            Assert.isTrue(StringUtils.hasText(this.groupId), "group id could not be null");
            if (!StringUtils.hasText(this.parentId)) {
                this.parentId = DEFAULT_PARENT_ID;
            }
            return new SolarRouter(this.id, this.routerName, this.routerLink, this.componentAddress,
                    this.menuLevel, this.sort, this.authorityCode, this.icon, this.groupId, this.parentId, this.hidden);
        }
    }

}
