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
package com.mtfm.security.core;

import java.io.Serializable;

/**
 * 当前会话请求详情
 * @author 一块小饼干
 * @since 1.0.0
 */
public class SessionRequest implements Serializable {

    /**
     * 请求ip地址
     */
    private String ip;

    /**
     * 请求物理地址
     */
    private String address;

    /**
     * 认证平台
     */
    private String platform;

    /**
     * 所属客户端
     */
    private String client;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "SessionRequest{" +
                "ip='" + ip + '\'' +
                ", address='" + address + '\'' +
                ", platform='" + platform + '\'' +
                ", client='" + client + '\'' +
                '}';
    }
}
