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

/**
 * @author 一块小饼干
 * @since 1.0.0
 */
public class RequestPayload implements Payload {

    private final String id;

    private final String sessionKey;

    private final boolean isEmpty;

    public RequestPayload(String id, String sessionKey, boolean isEmpty) {
        this.id = id;
        this.sessionKey = sessionKey;
        this.isEmpty = isEmpty;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getSessionKey() {
        return this.sessionKey;
    }

    @Override
    public boolean isEmpty() {
        return this.isEmpty;
    }

    public static RequestPayload carryToken(String id, String sessionKey) {
        return new RequestPayload(id, sessionKey, false);
    }

    public static RequestPayload unCarryToken(String id, String sessionKey) {
        return new RequestPayload(id, sessionKey, true);
    }
}
