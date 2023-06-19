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
package com.mtfm.core.context;

import java.nio.charset.Charset;

/**
 * 加解密
 * @author 一块小饼干
 * @since 1.0.0
 */
public interface Crypto {
    /**
     * 获取算法类型
     * @return 算法类型，默认转为大写
     */
    String getAlgorithm();

    /**
     * 解密
     * @param source 加密字符串
     * @return 解密后的字符串
     */
    String decrypt(String source);

    String decrypt(String source, Charset charset);

    /**
     * 加密
     * @param source 字符串源
     * @return 加密后的字符串
     */
    String encrypt(String source);

    String encrypt(String source, Charset charset);
}
