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

import com.mtfm.security.config.WebSecurityProperties;

/**
 * 多设备认证
 * @author 一块小饼干
 * @since 1.0.0
 */
public interface MultiAuthenticateMethodUserDetails {
    /**
     * 当前认证的方式
     * @return 认证方式
     */
    String getCurrentMethod();

    /**
     * 当前认证方式是否过期
     * @return 如果为true，则不需要进行重新认证该认证方式，反之则需要重新认证该方式，但是可以在
     * {@link WebSecurityProperties#isEnableMethodExpired()}自由的配置认证方式是否允许过期
     */
    boolean isCurrentMethodNonExpired();

    /**
     * 当前认证认证方式是否锁定
     * @return 如果为true，则不需要判定为锁定，反之则认证时使用该种认证方式的时候，不允许登陆
     */
    boolean isCurrentMethodNonLocked();

}
