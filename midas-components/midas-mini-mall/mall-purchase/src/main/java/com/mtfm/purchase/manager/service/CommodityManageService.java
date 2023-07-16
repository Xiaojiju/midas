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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.entity.Commodity;
import com.mtfm.purchase.entity.CommodityAttribute;
import com.mtfm.purchase.entity.CommodityImage;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.*;
import com.mtfm.purchase.manager.mapper.CommodityMapper;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommoditySplitDetails;
import com.mtfm.purchase.manager.provisioning.CommodityView;
import com.mtfm.purchase.manager.provisioning.Spu;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;
import com.mtfm.purchase.manager.service.bo.SplitPageQuery;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一块小饼干
 * @since 1.0.0
 *
 */
public class CommodityManageService extends ServiceImpl<CommodityMapper, Commodity> implements CommodityManager {

    private AttributeManager<CommodityAttribute> attributeManager;

    private ImageManager<CommodityImage> imageManager;

    private CommoditySkuRelationManager commoditySkuRelationManager;

    public CommodityManageService(AttributeManager<CommodityAttribute> attributeManager, ImageManager<CommodityImage> imageManager,
                                  CommoditySkuRelationManager commoditySkuRelationManager) {
        this.attributeManager = attributeManager;
        this.imageManager = imageManager;
        this.commoditySkuRelationManager = commoditySkuRelationManager;
    }

    @Override
    public long createCommodity(CommodityDetails details) {
        Long spuId = details.getSpuId();
        if (spuId == null) {
            throw new PurchaseNotFoundException("");
        }
        Commodity commodity = Commodity.uncreatedBuilder(details.getSpuId())
                .setName(details.getCommodityName())
                .setWeight(details.getWeight())
                .setPrice(details.getPrice())
                .setTitle(details.getTitle(), details.getSubtitle())
                .hadSale(0)
                .listingStocks(details.getStocks())
                .build();
        this.save(commodity);
        this.attributeManager.setAttributes(commodity.getId(), details.getCommodityAttributes());
        this.imageManager.setImages(commodity.getId(), details.getSkuImages());
        List<Long> skuItems = null;
        if (!CollectionUtils.isEmpty(details.getSkuVals())) {
            skuItems = details.getSkuVals().stream().map(Spu.SkuVal::getId).collect(Collectors.toList());
        }
        this.commoditySkuRelationManager.withSku(details.getSpuId(), commodity.getId(), skuItems);
        return commodity.getId();
    }

    @Override
    public void updateCommodity(CommodityDetails details) {
        Commodity commodity = this.getById(details.getId());
        if (commodity == null) {
            throw new PurchaseNotFoundException("没有找到对应规格商品");
        }
        Commodity prepare = Commodity.createdBuilder(commodity.getId(), commodity.getSpuId())
                .setName(details.getCommodityName())
                .setWeight(details.getWeight())
                .setPrice(details.getPrice())
                .setTitle(details.getTitle(), details.getSubtitle())
                .hadSale(0)
                .listingStocks(details.getStocks())
                .build();
        this.updateById(prepare);
        this.attributeManager.setAttributes(commodity.getId(), details.getCommodityAttributes());
        this.imageManager.setImages(commodity.getId(), details.getSkuImages());
        List<Long> skuItems = null;
        if (!CollectionUtils.isEmpty(details.getSkuVals())) {
            skuItems = details.getSkuVals().stream().map(Spu.SkuVal::getId).collect(Collectors.toList());
        }
        this.commoditySkuRelationManager.withSku(details.getSpuId(), commodity.getId(), skuItems);
    }

    @Override
    public void deleteByCommodityId(long id) {
        this.removeById(id);
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
        return this.baseMapper.selectCommodityDetails(spuId);
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
