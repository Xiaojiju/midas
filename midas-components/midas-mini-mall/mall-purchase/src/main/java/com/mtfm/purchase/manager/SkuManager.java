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

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtfm.purchase.entity.SkuItem;
import com.mtfm.purchase.manager.provisioning.Spu;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 规格管理
 */
public interface SkuManager extends IService<SkuItem> {

    /**
     * 为spu设置规格
     * @param spu spuId
     * @param skuItems 规格，包含规格对应的所有可选值
     */
    void setSkuItems(long spu, long category, List<Spu.SkuItemGroup> skuItems);

    /**
     * 移除spu的规格
     * @param spu spu id
     * @param items 规格
     */
    void removeSku(long spu, long[] items);

    /**
     * 获取spu设定的规格组
     * @param spu spu id
     * @return 规格组
     */
    List<Spu.SkuItemGroup> loadSpuSkuItems(long spu);

    /**
     * 通过分类名获取规格
     * @param category 分类id
     * @return 规格
     */
    List<SkuItem> loadItemsByCategory(long category, String itemName);
}
