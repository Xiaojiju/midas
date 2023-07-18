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
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SkuManageService.class);

    private SkuItemValueManager skuItemValueManager;

    public SkuManageService(SkuItemValueManager skuItemValueManager) {
        this.skuItemValueManager = skuItemValueManager;
    }

    @Override
    public void setSkuItems(long spu, List<SpuDetails.SkuItemGroup> skuItems) {
        if (CollectionUtils.isEmpty(skuItems)) {
            this.skuItemValueManager.removeBySpuId(spu, null);
            return ;
        }

        // 排除已经存在的规格
        List<SkuItem> items = this.saveOrUpdateSkuItems(skuItems);

        // 编排规格值
        List<SkuItemValue> values = new ArrayList<>();
        for (SpuDetails.SkuItemGroup group : skuItems) {
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
            List<SpuDetails.SkuVal> skuValues = group.getSkuValues();
            for (SpuDetails.SkuVal val : skuValues) {
                if (StringUtils.hasText(val.getItemValue())) {
                    values.add(new SkuItemValue(
                            null, spu, group.getSkuId(), group.getItemName(), val.getItemImage(), val.getItemValue()
                            )
                    );
                }
            }
        }

        //如果组装验证后的sku items为空，则不进行操作
        if (CollectionUtils.isEmpty(values)) {
            if (logger.isDebugEnabled()) {
                logger.debug("spu id: {}, the array of sku item that is passed by validation is empty", spu);
            }
            return ;
        }

        // 删除原先的规格，这里使用较为暴力的方式，如果数据量加大，需要优化，先查询出已经存在的规格，
        // 然后比对现有的规格，然后差量更新，减少sql执行
        this.skuItemValueManager.removeBySpuId(spu, null);

        this.skuItemValueManager.setSkuValues(values);
    }

    @Override
    public void removeSku(long spu, long[] items) {
        this.skuItemValueManager.removeBySpuId(spu, items);
    }

    @Override
    public List<SpuDetails.SkuItemGroup> loadSpuSkuItems(long spu) {
        return this.baseMapper.selectSkuGroupsBySpu(spu);
    }

    @Override
    public List<SkuItem> loadItemsByCategory(String itemName) {
        QueryWrapper<SkuItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(StringUtils.hasText(itemName), SkuItem::getItemName, itemName);
        return this.list(queryWrapper);
    }

    private List<SkuItem> saveOrUpdateSkuItems(List<SpuDetails.SkuItemGroup> skuItems) {
        List<Sample> filter = this.filter(
                skuItems.stream().map(SpuDetails.SkuItemGroup::getItemName).collect(Collectors.toList()));
        List<SkuItem> items = new ArrayList<>();
        boolean exist = false;
        for (SpuDetails.SkuItemGroup group : skuItems) {
            String itemName = group.getItemName();
            if (!StringUtils.hasText(itemName)) {
                continue;
            }
            for (Sample sample : filter) {
                if (sample.getValue().equals(itemName)) {
                    items.add(new SkuItem(sample.getId(), sample.getValue(), group.getIcon()));
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                items.add(new SkuItem(null, group.getItemName(), group.getIcon()));
            }
            exist = false;
        }
        this.saveOrUpdateBatch(items);
        return items;
    }

    private List<Sample> filter(List<String> itemNames) {
        QueryWrapper<SkuItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(SkuItem::getItemName, itemNames);
        List<SkuItem> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(item -> new Sample(item.getId(), item.getItemName())).collect(Collectors.toList());
    }

    protected SkuItemValueManager getSkuItemValueManager() {
        return skuItemValueManager;
    }

    public void setSkuItemValueManager(SkuItemValueManager skuItemValueManager) {
        this.skuItemValueManager = skuItemValueManager;
    }
}
