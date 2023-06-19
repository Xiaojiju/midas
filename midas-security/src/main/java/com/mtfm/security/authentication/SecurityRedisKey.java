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
package com.mtfm.security.authentication;

import com.mtfm.core.RedisKey;

import java.util.Arrays;

/**
 * @author 一块小饼干
 * reddis key 常量
 */
public final class SecurityRedisKey extends RedisKey {

    private static final String KEY_SEPARATOR = ":";
    public static final String SCAN_ALL = "*";
    public static final String HEADER_TOKEN = "authentication:token";
    public static final String TARGET_KEY_BLACK_ITEM = "security:target_key:black_item";
    public static final String TARGET_KEY_FAIL_COUNTER = "security:target_key:fail_counter";
    public static final String USER_STORAGE = "security:target_key:user_storage";
    public static final String TARGET_USER_CACHE = "target_user:target_key";



    public static String makeKey(String key, String...patterns) {
        StringBuffer buffer = new StringBuffer(key);
        Arrays.stream(patterns).sorted().forEach(item -> buffer.append(KEY_SEPARATOR).append(item));
        return buffer.toString();
    }
}
