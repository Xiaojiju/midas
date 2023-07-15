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
import com.mtfm.purchase.entity.SkuItem;
import com.mtfm.purchase.entity.SkuItemValue;
import com.mtfm.purchase.manager.SkuItemValueManager;
import com.mtfm.purchase.manager.SkuManager;
import com.mtfm.purchase.manager.mapper.SkuItemMapper;
import com.mtfm.purchase.manager.provisioning.Sample;
import com.mtfm.purchase.manager.provisioning.Spu;
import com.mtfm.tools.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 规格管理
 */
@Transactional(rollbackFor = Exception.class)
public class SkuManageService extends ServiceImpl<SkuItemMapper, SkuItem> implements SkuManager {

    private SkuItemValueManager skuItemValueManager;

    public SkuManageService(SkuItemValueManager skuItemValueManager) {
        this.skuItemValueManager = skuItemValueManager;
    }

    @Override
    public void setSkuItems(long spu, long category, List<Spu.SkuItemGroup> skuItems) {
        if (CollectionUtils.isEmpty(skuItems)) {
            return ;
        }

        // 排除已经存在的规格
        List<SkuItem> items = this.saveOrUpdateSkuItems(category, skuItems);

        // 编排规格值
        List<SkuItemValue> values = new ArrayList<>();
        for (Spu.SkuItemGroup group : skuItems) {
            String itemName = group.getItemName();
            for (SkuItem item : items) {
                if (item.getItemName().equals(itemName)) {
                    group.setSkuId(item.getId());
                    break;
                }
            }
            if (group.getSkuId() == null) {
                continue;
            }
            List<Spu.SkuVal> skuValues = group.getSkuValues();
            for (Spu.SkuVal val : skuValues) {
                values.add(new SkuItemValue(null, spu, group.getSkuId(), group.getItemName(), val.getItemImage(), val.getItemValue()));
            }
        }
        // 删除原先的规格，这里使用较为暴力的方式，如果数据量加大，需要优化，先查询出已经存在的规格，然后比对现有的规格，然后差量更新，减少sql执行
        this.skuItemValueManager.removeBySpuId(spu, null);
        this.skuItemValueManager.setSkuValues(values);
    }

    @Override
    public void removeSku(long spu, long[] items) {
        this.skuItemValueManager.removeBySpuId(spu, items);
    }

    @Override
    public List<Spu.SkuItemGroup> loadSpuSkuItems(long spu) {
        return this.baseMapper.selectSkuGroupsBySpu(spu);
    }

    @Override
    public List<SkuItem> loadItemsByCategory(long category, String itemName) {
        QueryWrapper<SkuItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SkuItem::getCategoryId, category)
                .like(StringUtils.hasText(itemName), SkuItem::getItemName, itemName);
        return this.list(queryWrapper);
    }

    private List<SkuItem> saveOrUpdateSkuItems(long category, List<Spu.SkuItemGroup> skuItems) {
        List<Sample> filter = this.filter(category,
                skuItems.stream().map(Spu.SkuItemGroup::getItemName).collect(Collectors.toList()));
        List<SkuItem> items = new ArrayList<>();
        boolean exist = false;
        for (Spu.SkuItemGroup group : skuItems) {
            String itemName = group.getItemName();
            if (!StringUtils.hasText(itemName)) {
                continue;
            }
            for (Sample sample : filter) {
                if (sample.getValue().equals(itemName)) {
                    items.add(new SkuItem(sample.getId(), category, sample.getValue(), group.getIcon()));
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                items.add(new SkuItem(null, category, group.getItemName(), group.getIcon()));
            }
            exist = false;
        }
        this.saveOrUpdateBatch(items);
        return items;
    }

    private List<Sample> filter(long category, List<String> itemNames) {
        QueryWrapper<SkuItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SkuItem::getCategoryId, category)
                .in(SkuItem::getItemName, itemNames);
        return this.listObjs(queryWrapper, (result) -> {
            SkuItem skuItem = (SkuItem) result;
            return new Sample(skuItem.getId(), skuItem.getItemName());
        });
    }

    protected SkuItemValueManager getSkuItemValueManager() {
        return skuItemValueManager;
    }

    public void setSkuItemValueManager(SkuItemValueManager skuItemValueManager) {
        this.skuItemValueManager = skuItemValueManager;
    }
}
