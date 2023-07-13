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

import com.mtfm.purchase.manager.provisioning.AttributeGroupValue;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 属性管理
 */
public interface AttributeManager {
    /**
     * 设置spu的属性
     * @param spu spu id
     * @param attributeGroupValues 分组属性
     */
    void setSpuAttributes(long spu, List<AttributeGroupValue> attributeGroupValues);

    /**
     * 删除spu的属性
     * @param spu spu id
     * @param attributes 属性id
     */
    void removeSpuAttributes(long spu, long[] attributes);

    /**
     * 获取spu的属性
     * @param id spu id
     * @return 属性分组值
     */
    List<AttributeGroupValue> loadAttributesBySpuId(long id);

    /**
     * 设置具体商品的属性
     * @param commodity spu下的商品id
     * @param attributeGroupValues 分组属性值
     */
    void setCommodityAttributes(long commodity, List<AttributeGroupValue> attributeGroupValues);

    /**
     * 删除具体商品属性
     * @param commodity spu下的商品id
     * @param attributes 属性
     */
    void removeCommodityAttributes(long commodity, long[] attributes);

    /**
     * 获取具体商品的属性分组
     * @param id spu下的具体商品
     * @return 分组属性
     */
    List<AttributeGroupValue> loadAttributesByCommodityId(long id);
}
