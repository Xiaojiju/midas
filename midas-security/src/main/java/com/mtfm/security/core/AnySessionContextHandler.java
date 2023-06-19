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

import com.mtfm.security.authentication.SecurityRedisKey;
import com.mtfm.tools.JSONUtils;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

/**
 * 远程redis缓存上下文处理累，直接操作多设备会话上下文
 * @author 一块小饼干
 * @since 1.0.0
 */
public class AnySessionContextHandler implements AnySessionContext<UserSubject> {

    private RedisTemplate<String, String> redisTemplate;

    public AnySessionContextHandler(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserSubject getSession(String id) {
        BoundValueOperations<String, String> ops = ops(id);
        String result = ops.get();
        if (StringUtils.hasText(result)) {
            return JSONUtils.parse(result, UserSubject.class);
        }
        return null;
    }

    @Override
    public void putSession(String id, UserSubject subject) {
        BoundValueOperations<String, String> ops = ops(id);
        ops.set(JSONUtils.toJsonString(subject));
    }

    @Override
    public void clear(String id) {
        this.redisTemplate.delete(getKey(id));
    }

    private BoundValueOperations<String, String> ops(String key) {
        return redisTemplate.boundValueOps(getKey(key));
    }

    private String getKey(String key) {
        return SecurityRedisKey.makeKey(SecurityRedisKey.USER_STORAGE, key);
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
