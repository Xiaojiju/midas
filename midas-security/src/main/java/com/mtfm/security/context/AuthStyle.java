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
package com.mtfm.security.context;

/**
 * 登陆状态控制枚举，默认为{@link AuthStyle#SINGLE}
 * todo 暂时仅仅实现了单一的认证方式{@link SimpleSingleHoldingProvider}，待有需求再实现其他的情况
 * @author 一块小饼干
 * @since 1.0.0
 */
public enum AuthStyle {

    /**
     * 全平台仅允许一个地方登陆，仅保持最新的登陆状态
     */
    SINGLE("single"),
    /**
     * 允许多平台登陆，但是同平台多地登陆只保持最新的登陆状态
     */
    PLATFORM("platform"),
    /**
     * 允许多地登陆，慎用
     */
    ANY("any");

    private final String value;

    AuthStyle(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
