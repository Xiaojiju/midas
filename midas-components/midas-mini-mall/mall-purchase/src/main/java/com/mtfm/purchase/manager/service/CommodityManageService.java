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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.PurchaseMessageSource;
import com.mtfm.purchase.entity.Commodity;
import com.mtfm.purchase.entity.CommodityAttribute;
import com.mtfm.purchase.entity.CommodityImage;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.AttributeManager;
import com.mtfm.purchase.manager.CommodityManager;
import com.mtfm.purchase.manager.CommoditySkuRelationManager;
import com.mtfm.purchase.manager.ImageManager;
import com.mtfm.purchase.manager.mapper.CommodityMapper;
import com.mtfm.purchase.manager.provisioning.*;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;
import com.mtfm.purchase.manager.service.bo.SplitPageQuery;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品管理实现
 */
@Transactional(rollbackFor = Exception.class)
public class CommodityManageService extends ServiceImpl<CommodityMapper, Commodity> implements CommodityManager, MessageSourceAware {

    private AttributeManager<CommodityAttribute> attributeManager;

    private ImageManager<CommodityImage> imageManager;

    private CommoditySkuRelationManager commoditySkuRelationManager;

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();

    public CommodityManageService(AttributeManager<CommodityAttribute> attributeManager,
                                  ImageManager<CommodityImage> imageManager,
                                  CommoditySkuRelationManager commoditySkuRelationManager) {
        this.attributeManager = attributeManager;
        this.imageManager = imageManager;
        this.commoditySkuRelationManager = commoditySkuRelationManager;
    }

    @Override
    public long createCommodity(CommodityDetails details) throws PurchaseNotFoundException {
        Long spuId = details.getSpuId();
        if (spuId == null) {
            throw new PurchaseNotFoundException(this.messages.getMessage("SpuDetailsService.notFound",
                    "Unable to find the specified product."));
        }
        Commodity commodity = Commodity.uncreatedBuilder(details.getSpuId())
                .setName(details.getCommodityName())
                .setWeight(details.getWeight())
                .setPrice(details.getPrice(), details.getCurrentPrice(), details.getVipPrice())
                .setTitle(details.getTitle(), details.getSubtitle())
                .hadSale(0)
                .listingStocks(details.getStocks())
                .build();
        this.save(commodity);
        this.attributeManager.setAttributes(commodity.getId(), details.getCommodityAttributes());
        this.imageManager.setImages(commodity.getId(), details.getSkuImages());
        List<Long> skuItems = null;
        if (!CollectionUtils.isEmpty(details.getSkuVals())) {
            skuItems = details.getSkuVals().stream().map(SpuDetails.SkuVal::getId).collect(Collectors.toList());
        }
        this.commoditySkuRelationManager.withSku(details.getSpuId(), commodity.getId(), skuItems);
        return commodity.getId();
    }

    @Override
    public void updateCommodity(CommodityDetails details) throws PurchaseNotFoundException {
        Commodity commodity = this.getById(details.getId());
        if (commodity == null) {
            throw new PurchaseNotFoundException(this.messages.getMessage("CommodityManageService.notFound",
                    "could not found commodity, maybe not exist."));
        }
        Commodity prepare = Commodity.createdBuilder(commodity.getId(), commodity.getSpuId())
                .setName(details.getCommodityName())
                .setWeight(details.getWeight())
                .setPrice(details.getPrice(), details.getCurrentPrice(), details.getVipPrice())
                .setTitle(details.getTitle(), details.getSubtitle())
                .hadSale(0)
                .listingStocks(details.getStocks())
                .build();
        this.updateById(prepare);
        this.attributeManager.setAttributes(commodity.getId(), details.getCommodityAttributes());
        this.imageManager.setImages(commodity.getId(), details.getSkuImages());
        List<Long> skuItems = null;
        if (!CollectionUtils.isEmpty(details.getSkuVals())) {
            skuItems = details.getSkuVals().stream().map(SpuDetails.SkuVal::getId).collect(Collectors.toList());
        }
        this.commoditySkuRelationManager.withSku(details.getSpuId(), commodity.getId(), skuItems);
    }

    @Override
    public void deleteByCommodityId(long id) {
        if (!this.removeById(id)) {
            throw new PurchaseNotFoundException(this.messages.getMessage("CommodityManageService.notFound",
                    "could not found commodity, maybe not exist."));
        }
    }

    @Override
    public void deleteBySpuId(long id) {
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Commodity::getSpuId, id);
        this.remove(queryWrapper);
    }

    @Override
    public PageTemplate<CommoditySplitDetails> loadPage(SplitPageQuery query) {
        long current = query.getCurrent();
        query.setCurrent((current - 1) * query.getSize());
        List<CommoditySplitDetails> commoditySplitDetails = this.baseMapper.selectCommodities(query);
        long total = this.baseMapper.selectSpuCount(query);
        return new PageTemplate<>(current, query.getSize(), total, commoditySplitDetails);
    }

    @Override
    public List<CommodityDetails> loadCommodities(long spuId) {
        List<CommodityDetails> commodityDetails = this.baseMapper.selectCommodityDetails(spuId);
        if (CollectionUtils.isEmpty(commodityDetails)) {
            return new ArrayList<>();
        }
        return commodityDetails;
    }

    @Override
    public PageTemplate<CommodityView> loadCommodityViewPage(CommodityPageQuery query) {
        Page<CommodityView> page = new Page<CommodityView>().setSize(query.getSize()).setCurrent(query.getCurrent());
        page = this.baseMapper.selectViews(page, query);
        return new PageTemplate<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    @Override
    public CommodityDetails loadCommodityById(long commodityId) {
        return this.baseMapper.selectCommodityById(commodityId);
    }

    @Override
    public List<Sku> loadEachStocks(long spuId) {
        return this.baseMapper.selectStocks(spuId);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected AttributeManager<CommodityAttribute> getAttributeManager() {
        return attributeManager;
    }

    public void setAttributeManager(AttributeManager<CommodityAttribute> attributeManager) {
        this.attributeManager = attributeManager;
    }

    protected ImageManager<CommodityImage> getImageManager() {
        return imageManager;
    }

    public void setImageManager(ImageManager<CommodityImage> imageManager) {
        this.imageManager = imageManager;
    }

    protected CommoditySkuRelationManager getCommoditySkuRelationManager() {
        return commoditySkuRelationManager;
    }

    public void setCommoditySkuRelationManager(CommoditySkuRelationManager commoditySkuRelationManager) {
        this.commoditySkuRelationManager = commoditySkuRelationManager;
    }
}
