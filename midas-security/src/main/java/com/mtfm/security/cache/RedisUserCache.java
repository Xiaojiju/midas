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
package com.mtfm.security.cache;

import com.mtfm.security.AppUser;
import com.mtfm.security.authentication.SecurityRedisKey;
import com.mtfm.tools.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * redis缓存
 * 在认证{@link org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#authenticate(Authentication)}中，
 * UserDetails user = this.userCache.getUserFromCache(username);
 * 优先缓存中获取
 * @author 一块小饼干
 * @since 1.0.0
 */
public class RedisUserCache implements UserCache {

    protected Logger logger = LoggerFactory.getLogger(RedisUserCache.class);
    private final RedisTemplate<String, String> redisTemplate;

    public RedisUserCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserDetails getUserFromCache(String username) {
        BoundValueOperations<String, String> ops = getOperations(username);
        String details = ops.get();
        if (StringUtils.hasText(details)) {
            try {
                return JSONUtils.parse(details, AppUser.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public void putUserInCache(UserDetails user) {
        if (user == null) {
            return ;
        }
        try {
            AppUser authority = (AppUser) user;
            String authorityValue = JSONUtils.toJsonString(authority);
            BoundValueOperations<String, String> ops = getOperations(authority.getUsername());
            ops.set(authorityValue);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("UserDetails cast to UserTemplate error");
            }
        }
    }

    @Override
    public void removeUserFromCache(String username) {
        if (!StringUtils.hasText(username)) {
            return ;
        }
        redisTemplate.delete(getKey(username));
    }

    private BoundValueOperations<String, String> getOperations(String username) {
        String key = getKey(username);
        return redisTemplate.boundValueOps(key);
    }

    private String getKey(String username) {
        return SecurityRedisKey.makeKey(SecurityRedisKey.TARGET_USER_CACHE, username);
    }
}
