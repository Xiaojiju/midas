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

import org.springframework.util.StringUtils;

/**
 * 客户端种类
 * @author 一块小饼干
 * @since 1.0.0
 */
public enum Client {
    /**
     * web端，主要为网页
     */
    WEB("web"),
    /**
     * windows客户端
     */
    PC_CLIENT("pc_client"),
    /**
     * macOS客户端
     */
    MAC_CLIENT("mac_client"),
    /**
     * 移动App
     */
    MOBILE_CLIENT("mobile_client"),
    /**
     * 小程序
     */
    MINI_PROGRAM("mini_program");

    private final String name;

    Client(String name) {
        this.name = name;
    }

    public static String indexOf(String name) {
        if (!StringUtils.hasText(name)) {
            return WEB.name;
        }
        Client[] clients = Client.values();
        for (Client client : clients) {
            if (client.name.equals(name)) {
                return client.name;
            }
        }
        return WEB.name;
    }

    public String getName() {
        return name;
    }
}
