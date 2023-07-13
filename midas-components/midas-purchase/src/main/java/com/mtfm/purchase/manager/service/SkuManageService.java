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
package com.mtfm.purchase.manager.service;

import com.mtfm.purchase.entity.SkuItem;
import com.mtfm.purchase.manager.SkuManager;
import com.mtfm.purchase.manager.provisioning.Spu;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 规格管理
 */
public class SkuManageService implements SkuManager {

    @Override
    public void setSkuItems(long spu, List<Spu.SkuItemGroup> skuItems) {

    }

    @Override
    public void removeSku(long spu, long[] items) {

    }

    @Override
    public void withSku(long commodity, long[] items) {

    }

    @Override
    public List<Spu.SkuItemGroup> loadSpuSkuItems(long spu) {
        return null;
    }

    @Override
    public List<Spu.SkuItemGroup> loadCommoditySkuItems(long commodity) {
        return null;
    }

    @Override
    public List<SkuItem> loadItemsByCategory(long category) {
        return null;
    }
}
