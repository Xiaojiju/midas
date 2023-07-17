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
import com.mtfm.purchase.entity.CommodityImage;
import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.manager.ImageManager;
import com.mtfm.purchase.manager.mapper.CommodityImageMapper;
import com.mtfm.tools.StringUtils;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 规格商品图片管理业务
 */
public class CommodityImageService extends ServiceImpl<CommodityImageMapper, CommodityImage> implements ImageManager<CommodityImage>, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(CommodityImageService.class);

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();

    @Override
    public void setImages(long id, List<CommodityImage> images) {
        this.removeImages(id, null);
        if (CollectionUtils.isEmpty(images)) {
            return ;
        }
        boolean primary = false;
        List<CommodityImage> passed = new ArrayList<>();
        for (CommodityImage commodityImage : images) {
            if (!StringUtils.hasText(commodityImage.getImageUrl())) {
                continue;
            }
            boolean setPrimary = commodityImage.getIndexImage() == Judge.YES;
            if (primary && setPrimary) {
                throw new PurchaseExistException(this.messages.getMessage("SpuImageService.primaryExist",
                        "cannot set multiple images as the primary image"));
            }
            primary = setPrimary;
            commodityImage.setCommodityId(id);
            commodityImage.setId(null);
            passed.add(commodityImage);
        }
        if (CollectionUtils.isEmpty(passed)) {
            if (logger.isDebugEnabled()) {
                logger.debug("commodity image array is empty, maybe element contain empty value in source data");
            }
            return ;
        }
        this.saveBatch(passed);
    }

    @Override
    public void removeImages(long id, Collection<Long> images) {
        QueryWrapper<CommodityImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityImage::getCommodityId, id)
                .in(!CollectionUtils.isEmpty(images), CommodityImage::getId, images);
        this.remove(queryWrapper);
    }

    @Override
    public List<CommodityImage> loadImages(long id) {
        QueryWrapper<CommodityImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityImage::getId, id);
        List<CommodityImage> spuImages = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(spuImages)) {
            return new ArrayList<>();
        }
        return spuImages;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
