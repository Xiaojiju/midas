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
package com.mtfm.security.config;

import com.mtfm.security.SecurityConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 用户认证信息过滤配置文件
 * 主要体现在根据需求配置认证信息或认证方式的限制条件
 * 比如：需求需要账户可以建立子账号，但是子账号有过期时间，则需要对账户设置过期时间，则需要开启允许账户过期过滤，当然这个需求体现在多租户上，
 * 要想使用这个多租户的功能，需要引入security-plus才能进行使用
 * @author 一块小饼干
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = SecurityConstants.CONFIG_PREFIX)
public class WebSecurityProperties {
    /**
     * 开启权限认证，默认为开启状态
     */
    private boolean enablePermissions = true;
    /**
     * 允许账号过期
     */
    private boolean enableAccountExpired;
    /**
     * 允许密钥过期
     * 若关闭了密钥过期，当密钥过期后，依然会提示过期，但是不会强制用户进行更新
     */
    private boolean enableCredentialsExpired;
    /**
     * 允许认证方式过期
     * 若关闭了认证方式过期，当认证方式过期后，依然会提示过期，但是不会强制要求用户更新
     */
    private boolean enableMethodExpired;

    public boolean isEnablePermissions() {
        return enablePermissions;
    }

    public void setEnablePermissions(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }

    public boolean isEnableAccountExpired() {
        return enableAccountExpired;
    }

    public void setEnableAccountExpired(boolean enableAccountExpired) {
        this.enableAccountExpired = enableAccountExpired;
    }

    public boolean isEnableCredentialsExpired() {
        return enableCredentialsExpired;
    }

    public void setEnableCredentialsExpired(boolean enableCredentialsExpired) {
        this.enableCredentialsExpired = enableCredentialsExpired;
    }

    public boolean isEnableMethodExpired() {
        return enableMethodExpired;
    }

    public void setEnableMethodExpired(boolean enableMethodExpired) {
        this.enableMethodExpired = enableMethodExpired;
    }

}
