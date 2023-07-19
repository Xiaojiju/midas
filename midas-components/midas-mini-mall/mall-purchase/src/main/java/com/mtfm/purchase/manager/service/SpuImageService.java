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
import com.mtfm.purchase.entity.SpuImage;
import com.mtfm.purchase.manager.ImageManager;
import com.mtfm.purchase.manager.mapper.SpuImageMapper;
import com.mtfm.tools.StringUtils;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * spu图片管理业务实现
 */
@Transactional(rollbackFor = Exception.class)
public class SpuImageService extends ServiceImpl<SpuImageMapper, SpuImage> implements ImageManager<SpuImage> {

    private static final Logger logger = LoggerFactory.getLogger(SpuDetailsService.class);

    @Override
    public void setImages(long spu, List<SpuImage> images) {
        if (CollectionUtils.isEmpty(images)) {
            // 删除spu下的图片
            this.removeImages(spu, null);
            return ;
        }
        boolean primary = false;
        List<SpuImage> passed = new ArrayList<>();
        for (SpuImage spuImage : images) {
            // 如果图片没有设置图片地址，则不进行设置
            if (!StringUtils.hasText(spuImage.getImageUrl())) {
                continue;
            }
            boolean setPrimary = spuImage.getIndexImage() == Judge.YES;
            if (primary && setPrimary) {
                // 如果已经设置了主图，则后面所有设置了主图的图片不进行设置
                spuImage.setIndexImage(Judge.NO);

                if (logger.isDebugEnabled()) {
                    logger.debug("spu id: {}, could not set multiple images as the primary image, if so, default set " +
                            "first image as the primary image", spu);
                }

            }
            primary = setPrimary;
            spuImage.setSpuId(spu);
            spuImage.setId(null);
            passed.add(spuImage);
        }

        if (CollectionUtils.isEmpty(passed)) {
            if (logger.isDebugEnabled()) {
                logger.debug("spu image array is empty, maybe element contain empty value in source data");
            }
            return ;
        }

        // 如果没有主图，则默认第一张为主图
        if (!primary) {
            SpuImage spuImage = passed.get(0);
            spuImage.setIndexImage(Judge.YES);
            passed.set(0, spuImage);
        }
        // 删除spu下的图片
        this.removeImages(spu, null);

        this.saveBatch(passed);
    }

    @Override
    public void removeImages(long spu, Collection<Long> images) {
        QueryWrapper<SpuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SpuImage::getSpuId, spu)
                .in(!CollectionUtils.isEmpty(images), SpuImage::getId, images);
        this.remove(queryWrapper);
    }

    @Override
    public List<SpuImage> loadImages(long spu) {
        QueryWrapper<SpuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SpuImage::getSpuId, spu);
        List<SpuImage> spuImages = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(spuImages)) {
            return new ArrayList<>();
        }
        return spuImages;
    }
}
