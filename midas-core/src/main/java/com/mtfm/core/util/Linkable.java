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

import java.io.Serializable;
import java.util.List;
/**
 * 定义可被转为树形结构的标准
 * @param <T> 指定转为树形的类类型
 * @author 一块小饼干
 * @since 1.0.0
 */
public interface Linkable<T> extends Serializable {

    /**
     * 节点key
     * @return key
     * @since 1.0.0
     */
    String getKey();

    /**
     * 父级
     * @return 关联的key，此属性应是关联对象的key
     * @since 1.0.0
     */
    String getParent();

    /**
     * 子节点
     * @return 子节点列表
     * @since 1.0.0
     */
    List<T> getNodes();

    /**
     * 更新子节点
     * @param nodes 子节点
     */
    void setNodes(List<T> nodes);

    /**
     * 深度
     * @return 当前节点所处深度
     * @since 1.0.0
     */
    int getHeight();

    /**
     * 设置深度
     * @param height 深度
     * @since 1.0.0
     */
    void setHeight(int height);
}
