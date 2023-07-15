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
package com.mtfm.purchase.manager;

import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 属性管理
 */
public interface AttributeManager<T> {
    /**
     * 设置商品的属性
     * @param id 对应商品
     * @param attributeGroupValues 分组属性值
     */
    void setAttributes(long id, List<T> attributeGroupValues);

    /**
     * 删除具体商品属性
     * @param id 对应商品
     * @param attributes 属性
     */
    void removeAttributes(long id, Collection<Long> attributes);

    /**
     * 获取具体商品的属性分组
     * @param id 对应商品
     * @return 分组属性
     */
    List<T> loadAttributesById(long id);
}
