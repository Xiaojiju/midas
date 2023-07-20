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
 * spu图片管理
 */
public interface ImageManager<T> {
    /**
     * 为spu设置图片
     * @param id 商品id
     * @param images 图片组
     */
    void setImages(long id, List<T> images);

    /**
     * 删除spu的图片
     * @param id 商品id
     * @param images 需要删除的图片
     */
    void removeImages(long id, Collection<Long> images);

    /**
     * 获取对应spu的图片
     * @param id 商品id
     * @return 图片组
     */
    List<T> loadImages(long id);

}