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

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 清空会话事件
 * 清空会话时候，需要携带指定的用户id
 */
public class ClearSessionEvent extends ApplicationEvent {

    private String userId;

    public ClearSessionEvent(Object source, String userId) {
        super(source);
        this.userId = userId;
    }

    public ClearSessionEvent(Object source, Clock clock, String userId) {
        super(source, clock);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
