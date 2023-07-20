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
import com.mtfm.purchase.entity.SpuAttribute;
import com.mtfm.purchase.entity.SpuImage;
import com.mtfm.purchase.entity.StandardProductUnit;
import com.mtfm.purchase.exceptions.PurchaseNotAllowedListingException;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.*;
import com.mtfm.purchase.manager.mapper.SpuMapper;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.tools.StringUtils;
import com.mtfm.tools.enums.Judge;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * spu管理
 * 注意流程：
 *  添加一个系列的商品需要进行先创建商品的spu（可以看作系列，便于理解），然后再进行sku的关联具体规格的商品，然后添加具体的活动等额外的设置，一个
 *  商品就添加完成；例如流程：
 *  农夫山泉矿泉水，分为1.5L和3L以及500ML的3中规格，则：
 *  第一步：创建农夫山泉矿泉水这个商品系列，其中需要设置通用的商品属性，比如 来源：长白山，是否经过检测：通过国家ISO标准等都属于通用的属性，然后就是
 *  系列产品的规格，容量即为它的规格，然后创建好规格后，就是第一步需要完成设定；
 *  第二步：具体商品展示和库存
 *  第三步：可以是额外的设定，比如促销活动、无力等等；
 *  大致流程为上面的三个步骤，这样便于用户操作和理解；
 */
@Transactional(rollbackFor = Exception.class)
public class SpuDetailsService extends ServiceImpl<SpuMapper, StandardProductUnit> implements SpuManager, MessageSourceAware {

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();

    private ImageManager<SpuImage> spuImageManager;

    private AttributeManager<SpuAttribute> attributeValueManager;

    private SkuManager skuManager;

    private CommodityManager commodityManager;

    public SpuDetailsService(ImageManager<SpuImage> spuImageManager,
                             AttributeManager<SpuAttribute> attributeValueManager,
                             SkuManager skuManager,
                             CommodityManager commodityManager) {
        this.spuImageManager = spuImageManager;
        this.attributeValueManager = attributeValueManager;
        this.skuManager = skuManager;
        this.commodityManager = commodityManager;
    }

    @Override
    public boolean spuExist(String product) {
        if (!StringUtils.hasText(product)) {
            return true;
        }
        QueryWrapper<StandardProductUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StandardProductUnit::getProduct, product)
                .eq(StandardProductUnit::getDeleted, Judge.NO);
        return this.baseMapper.exists(queryWrapper);
    }

    @Override
    public void listing(long spuId, Judge grounding) throws PurchaseNotFoundException {
        StandardProductUnit spu = this.getById(spuId);
        if (spu == null) {
            throw new PurchaseNotFoundException(this.messages.getMessage("SpuDetailsService.notFound",
                    "unable to find the specified product"));
        }
        if (this.baseMapper.selectCommodityCount(spuId) <= 0L) {
            throw new PurchaseNotAllowedListingException(this.messages.getMessage("SpuDetailsService.notFound",
                    "unable to find the specified product"));
        }
        spu.setListing(grounding);
        this.updateById(spu);
    }

    @Override
    public long createSpu(SpuDetails spuDetails) {
        // 添加spu基本信息
        StandardProductUnit.SpuBuilder builder = StandardProductUnit.uncreated();
        StandardProductUnit spu = builder.withProductName(spuDetails.getProduct())
                .withCategoryId(spuDetails.getCategoryId())
                .withBrandId(1L)
                .withUnit(spuDetails.getUnit())
                .writeBrief(spuDetails.getBrief())
                .listing(Judge.NO)
                .build();
        this.save(spu);
        // 设置图片
        this.spuImageManager.setImages(spu.getId(), spuDetails.getImages());
        // 设置主要属性
        this.attributeValueManager.setAttributes(spu.getId(), spuDetails.getSpuAttributes());
        // 设置商品销售规格
        this.skuManager.setSkuItems(spu.getId(), spuDetails.getGroups());
        return spu.getId();
    }

    @Override
    public void updateSpu(SpuDetails spuDetails) {
        // 更新spu基本信息
        StandardProductUnit.SpuBuilder builder = StandardProductUnit.created(spuDetails.getId());
        StandardProductUnit spu = builder.withProductName(spuDetails.getProduct())
                .withCategoryId(spuDetails.getCategoryId())
                .withBrandId(1L)
                .withUnit(spuDetails.getUnit())
                .writeBrief(spuDetails.getBrief())
                .listing(spuDetails.getListing())
                .build();
        this.updateById(spu);
        // 更新spu图片
        this.spuImageManager.setImages(spu.getId(), spuDetails.getImages());
        // 设置主要属性
        this.attributeValueManager.setAttributes(spu.getId(), spuDetails.getSpuAttributes());
        // 设置规格
        this.skuManager.setSkuItems(spu.getId(), spuDetails.getGroups());
    }

    @Override
    public SpuDetails loadSpuDetailsById(long spuId) throws PurchaseNotFoundException {
        SpuDetails spu = this.baseMapper.selectSpuById(spuId);
        if (spu == null) {
            throw new PurchaseNotFoundException(this.messages.getMessage("SpuDetailsService.notFound",
                    "Unable to find the specified product"));
        }
        spu.setSpuAttributes(this.attributeValueManager.loadAttributesById(spuId));
        spu.setGroups(this.skuManager.loadSpuSkuItems(spuId));
        spu.setImages(this.spuImageManager.loadImages(spuId));
        spu.setSkus(this.commodityManager.loadEachStocks(spuId));
        return spu;
    }

    @Override
    public void removeSpuById(long spuId) {
        StandardProductUnit spu = this.getById(spuId);
        if (spu == null) {
            throw new PurchaseNotFoundException(this.messages.getMessage("SpuDetailsService.notFound",
                    "Unable to find the specified product"));
        }
        spu.setListing(Judge.NO);
        spu.setDeleted(Judge.YES.getCode());
        this.updateById(spu);
    }

    @Override
    public long loadCount(long categoryId) {
        QueryWrapper<StandardProductUnit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StandardProductUnit::getCategoryId, categoryId)
                .eq(StandardProductUnit::getDeleted, Judge.NO);
        return this.count(queryWrapper);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected MessageSourceAccessor getMessages() {
        return messages;
    }

    public void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }

    protected ImageManager<SpuImage> getSpuImageManager() {
        return spuImageManager;
    }

    public void setSpuImageManager(ImageManager<SpuImage> spuImageManager) {
        this.spuImageManager = spuImageManager;
    }

    protected AttributeManager<SpuAttribute> getAttributeManager() {
        return attributeValueManager;
    }

    public void setAttributeManager(AttributeManager<SpuAttribute> attributeValueManager) {
        this.attributeValueManager = attributeValueManager;
    }

    protected SkuManager getSkuManager() {
        return skuManager;
    }

    public void setSkuManager(SkuManager skuManager) {
        this.skuManager = skuManager;
    }

    protected AttributeManager<SpuAttribute> getAttributeValueManager() {
        return attributeValueManager;
    }

    public void setAttributeValueManager(AttributeManager<SpuAttribute> attributeValueManager) {
        this.attributeValueManager = attributeValueManager;
    }

    protected CommodityManager getCommodityManager() {
        return commodityManager;
    }

    public void setCommodityManager(CommodityManager commodityManager) {
        this.commodityManager = commodityManager;
    }
}
