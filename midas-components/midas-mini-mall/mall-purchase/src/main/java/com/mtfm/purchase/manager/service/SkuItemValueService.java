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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.purchase.entity.SkuItemValue;
import com.mtfm.purchase.manager.SkuItemValueManager;
import com.mtfm.purchase.manager.mapper.SkuItemValueMapper;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 规格值管理
 */
public class SkuItemValueService extends ServiceImpl<SkuItemValueMapper, SkuItemValue> implements SkuItemValueManager {

    @Override
    public void removeBySpuId(long spuId, long[] items) {
        // 删除sku值
        QueryWrapper<SkuItemValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SkuItemValue::getSpuId, spuId)
                .eq(items != null && items.length > 0, SkuItemValue::getItemId, items);
        this.remove(queryWrapper);
    }

    @Override
    public void setSkuValues(List<SkuItemValue> values) {
        this.saveBatch(values);
    }
}
