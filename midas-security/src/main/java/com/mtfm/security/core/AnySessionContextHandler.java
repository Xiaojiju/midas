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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 远程redis缓存上下文处理累，直接操作多设备会话上下文
 * @author 一块小饼干
 * @since 1.0.0
 */
public class AnySessionContextHandler implements AnySessionContext<UserSubject>, ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AnySessionContextHandler.class);

    private RedisTemplate<String, String> redisTemplate;

    private String redisKey;

    @Autowired
    public AnySessionContextHandler() {
        this.redisKey = SecurityRedisKey.USER_STORAGE;
    }

    public AnySessionContextHandler(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisKey = SecurityRedisKey.USER_STORAGE;
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

    /**
     * 通过applicationContext注入RedisTemplate
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException 抛出bean异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 防止初始化redisTemplate不被覆盖，如果为空，则从容器当中取
        if (this.redisTemplate == null) {
            this.redisTemplate = (RedisTemplate<String, String>) applicationContext.getBean("redisTemplate");
        }
    }

    /**
     * 在setApplication后，检查是否正确注入RedisTemplate
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("RedisTemplate should be with <String, String>, check your configuration or bean");
        }
        Assert.notNull(this.redisTemplate, "redisTemplate could not be null, if you're using non-parameter " +
                "constructor,you could call constructor which is created by redisTemplate");
    }

    protected BoundValueOperations<String, String> ops(String key) {
        if (this.redisTemplate == null) {
            this.redisTemplate = new RedisTemplate<>();
        }
        return this.redisTemplate.boundValueOps(getKey(key));
    }

    protected String getKey(String key) {
        return SecurityRedisKey.makeKey(SecurityRedisKey.USER_STORAGE, key);
    }

    protected RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }
}
