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
package com.mtfm.core.util;

import java.util.List;
import java.util.function.Predicate;

/**
 * 简单的树形结构方法
 * @param <T> 节点类
 * @author 一块小饼干
 * @since 1.0.0
 */
public interface Tree<T> {

    /**
     * 在树上查找符合条件的节点
     * @param function {@link Predicate} 查找条件
     * @return 返回符合条件的节点以及其子节点
     * @since 1.0.0
     */
    Tree<T> find(Predicate<T> function);

    /**
     * 转为数组
     * @return 返回数组
     * @since 1.0.0
     */
    List<T> toArray();
}
