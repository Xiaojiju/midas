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

/**
 * 配置文件前缀
 * @author 一块小饼干
 * @since 1.0.0
 */
public final class SecurityConstants {
    /**
     * 认证方式配置
     */
    public static final String CONFIG_PREFIX = "solar.security";
    /**
     * session key配置
     */
    public static final String REQUEST_SESSION = "solar.auth";

    public static final String SESSION_UID_CLAIM = "u_id";

    public static final String SESSION_USERNAME = "username";

}
