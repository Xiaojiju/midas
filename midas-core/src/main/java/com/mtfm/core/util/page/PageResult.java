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

import java.util.Collection;

/**
 * 分页统一结果，其中指定返回的段落总数以及当前页的段落，如果当前包含的页为空，应返回空数组，防止null的出现；
 * @param <T> 分页结果的对象
 * @author 一块小饼干
 * @since 1.0.0
 */
public interface PageResult<T> extends Page {

    /**
     * 能够操作的段落的总数
     * @return 总数，应默认为0，且不能为负数
     */
    long getTotal();

    /**
     * 段落内容，段落内容应尽量排除Map等不显示显示内容构建对象的容器，若使用，则应该声明对象类来进行装配
     * @return 段落内容
     */
    Collection<T> getItems();
}
