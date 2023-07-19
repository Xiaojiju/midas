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
import com.mtfm.banner.entity.SwiperNotice;
import com.mtfm.banner.manager.SwiperNoticeManager;
import com.mtfm.banner.manager.provisioning.Notice;
import com.mtfm.banner.mapper.SwiperNoticeMapper;
import com.mtfm.core.util.page.Page;
import com.mtfm.core.util.page.PageTemplate;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 公告业务
 */
public class SwiperNoticeManageService extends ServiceImpl<SwiperNoticeMapper, SwiperNotice> implements SwiperNoticeManager {

    @Override
    public long createSwiperNotice(SwiperNotice swiperNotice) {
        this.save(swiperNotice);
        return swiperNotice.getId();
    }

    @Override
    public void updateSwiperNotice(SwiperNotice swiperNotice) {
        this.updateById(swiperNotice);
    }

    @Override
    public void removeSwiperNotice(long id) {
        this.removeById(id);
    }

    @Override
    public SwiperNotice loadNoticeDetails(long id) {
        return this.getById(id);
    }

    @Override
    public PageTemplate<Notice> loadNoticePage(Page page) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Notice> swiperPage
                = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        swiperPage.setCurrent(page.getCurrent())
                .setSize(page.getSize())
                .addOrder(OrderItem.desc("create_time"));
        swiperPage = this.baseMapper.selectNoticePage(swiperPage);
        return new PageTemplate<>(swiperPage.getCurrent(), swiperPage.getSize(), swiperPage.getTotal(), swiperPage.getRecords());
    }

    @Override
    public List<Notice> loadSwiper() {
        return this.baseMapper.selectNotices();
    }
}
