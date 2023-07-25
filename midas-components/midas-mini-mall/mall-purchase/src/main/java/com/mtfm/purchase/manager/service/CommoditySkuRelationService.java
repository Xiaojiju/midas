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
import com.mtfm.purchase.PurchaseMessageSource;
import com.mtfm.purchase.entity.CommoditySkuRelation;
import com.mtfm.purchase.exceptions.PurchaseRelationException;
import com.mtfm.purchase.manager.CommoditySkuRelationManager;
import com.mtfm.purchase.manager.SkuManager;
import com.mtfm.purchase.manager.mapper.CommoditySkuRelationMapper;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 规格商品关联管理
 */
@Transactional(rollbackFor = Exception.class)
public class CommoditySkuRelationService extends ServiceImpl<CommoditySkuRelationMapper, CommoditySkuRelation>
        implements CommoditySkuRelationManager, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(CommoditySkuRelationService.class);

    private SkuManager skuManager;

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();

    public CommoditySkuRelationService(SkuManager skuManager) {
        this.skuManager = skuManager;
    }

    @Override
    public List<SpuDetails.SkuVal> loadCommoditySkuItems(long commodity) {
        return this.baseMapper.selectSkuValues(commodity);
    }

    @Override
    public void withSku(long spuId, long commodity, List<Long> items) {
        if (items == null) {
            return ;
        }
        List<SpuDetails.SkuItemGroup> skuItemGroups = this.skuManager.loadSpuSkuItems(spuId);
        // 获取商品设定的销售规格,如果为空，则不需要操作
        if (CollectionUtils.isEmpty(skuItemGroups)) {
            return ;
        }
        int settingNum = items.size();
        if (CollectionUtils.isEmpty(items) || settingNum != skuItemGroups.size()) {
            throw new PurchaseRelationException(this.messages.getMessage("CommoditySkuRelationService.wrongSkuItem",
                    "the product specifications do not match the preset specifications."));
        }

        // 判断是否每个规格都设定
        for (SpuDetails.SkuItemGroup group : skuItemGroups) {
            List<SpuDetails.SkuVal> skuValues = group.getSkuValues();
            val:for (SpuDetails.SkuVal val : skuValues) {
                for (Long item : items) {
                    if (Objects.equals(val.getId(), item)) {
                        settingNum--;
                        break val;
                    }
                }
            }
        }

        // 判断是否已经存在相同规格
        if (settingNum != 0 || !this.setAllowed(spuId, items)) {
            throw new PurchaseRelationException(this.messages.getMessage("CommoditySkuRelationService.wrongSkuItem",
                    "the product specifications do not match the preset specifications."));
        }

        this.remove(
                new QueryWrapper<CommoditySkuRelation>()
                        .lambda()
                        .eq(CommoditySkuRelation::getCommodityId, commodity)
        );

        List<CommoditySkuRelation> relations = new ArrayList<>();
        items.forEach(item -> {
            CommoditySkuRelation relation = new CommoditySkuRelation();
            relation.setCommodityId(commodity);
            relation.setSkuId(item);
            relations.add(relation);
        });
        this.saveBatch(relations);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    private boolean setAllowed(long spuId, List<Long> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            return true;
        }
        List<String> skus = this.baseMapper.selectWithBatchSku(spuId);
        if (CollectionUtils.isEmpty(skus)) {
            return true;
        }
        skuIds.sort((o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            if (o1 < o2) {
                return -1;
            }
            return 1;
        });

        String join = String.join(",", skuIds.stream().map(String::valueOf).toArray(String[]::new));
        for (String sku : skus) {
            if (StringUtils.hasText(sku)) {
                if (sku.equals(join)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected SkuManager getSkuManager() {
        return skuManager;
    }

    public void setSkuManager(SkuManager skuManager) {
        this.skuManager = skuManager;
    }
}
