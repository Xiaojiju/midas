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
package com.mtfm.banner.manager.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.banner.entity.SwiperImage;
import com.mtfm.banner.manager.SwiperImageManager;
import com.mtfm.banner.manager.provisioning.Swiper;
import com.mtfm.banner.mapper.SwiperImageMapper;
import com.mtfm.core.util.page.Page;
import com.mtfm.core.util.page.PageTemplate;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 轮播图管理
 */
public class SwiperManageService extends ServiceImpl<SwiperImageMapper, SwiperImage> implements SwiperImageManager {

    @Override
    public long createSwiperImage(SwiperImage swiperImage) {
        this.save(swiperImage);
        return swiperImage.getId();
    }

    @Override
    public void updateSwiperImage(SwiperImage swiperImage) {
        this.updateById(swiperImage);
    }

    @Override
    public void removeSwiperImage(long id) {
        this.removeById(id);
    }

    @Override
    public SwiperImage loadSwiperDetails(long id) {
        return this.getById(id);
    }

    @Override
    public PageTemplate<Swiper> loadSwiperPage(Page page) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Swiper> swiperPage
                = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        swiperPage.setCurrent(page.getCurrent())
                .setSize(page.getSize())
                .addOrder(OrderItem.desc("create_time"));
        swiperPage = this.baseMapper.selectSwiperPage(swiperPage);
        return new PageTemplate<>(swiperPage.getCurrent(), swiperPage.getSize(), swiperPage.getTotal(), swiperPage.getRecords());
    }

    @Override
    public List<Swiper> loadSwiper() {
        return this.baseMapper.selectSwiper();
    }

}
