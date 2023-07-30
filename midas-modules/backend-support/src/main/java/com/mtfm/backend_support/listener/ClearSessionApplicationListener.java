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
package com.mtfm.backend_support.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 清空会话监听器
 * 主要用于在修改用户密码等功能后，需要用户重新认证或其他的场下的后置处理
 */
@Component
public class ClearSessionApplicationListener implements ApplicationListener<ClearSessionEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ClearSessionApplicationListener.class);

    public ClearSessionApplicationListener() {
    }

    @Async
    @Override
    public void onApplicationEvent(ClearSessionEvent event) {
        String userId = event.getUserId();
        if (!StringUtils.hasText(userId)) {
            if (logger.isDebugEnabled()) {
                logger.debug("userId is null, could clear session");
            }
        }
    }
}
