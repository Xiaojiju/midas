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
 * 会话配置
 * @author 一块小饼干
 * @scince 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = SecurityConstants.REQUEST_SESSION)
public class WebAuthProperties {
    /**
     * 自定义会话请求头
     */
    private String header;
    /**
     * 过期时间限制
     */
    private long expiredTimestamp;
    /**
     * 刷新时间限制
     */
    private long refreshTimestamp;
    /**
     * 是否允许自动刷新会话token
     */
    private boolean enableRefresh = true;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public long getExpiredTimestamp() {
        return expiredTimestamp;
    }

    public void setExpiredTimestamp(long expiredTimestamp) {
        this.expiredTimestamp = expiredTimestamp;
    }

    public long getRefreshTimestamp() {
        return refreshTimestamp;
    }

    public void setRefreshTimestamp(long refreshTimestamp) {
        this.refreshTimestamp = refreshTimestamp;
    }

    public boolean isEnableRefresh() {
        return enableRefresh;
    }

    public void setEnableRefresh(boolean enableRefresh) {
        this.enableRefresh = enableRefresh;
    }
}
