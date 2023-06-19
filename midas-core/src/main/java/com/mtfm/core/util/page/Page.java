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
package com.mtfm.core.util.page;

import java.io.Serializable;

/**
 * 定义查询分页的基本方法
 * @author 一块小饼干
 * @since 1.0.0
 */
public interface Page extends Serializable {

    long CURRENT = 1;
    long SIZE = 12;

    /**
     * 当前页码
     * @return 页码，不可以为负数
     * @since 1.0.0
     */
    long getCurrent();

    /**
     * 每页大小
     * @return 应当包含默认的大小，不可以为负数
     * @since 1.0.0
     */
    long getSize();

}
