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
package com.mtfm.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * 没有权限异常
 * @author 一块小饼干
 * @since 1.0.0
 */
public class NoAuthoritiesException extends AuthenticationException {

    public NoAuthoritiesException(String msg) {
        super(msg);
    }

    public NoAuthoritiesException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
