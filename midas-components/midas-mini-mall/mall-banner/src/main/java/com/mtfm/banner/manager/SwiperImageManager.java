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
package com.mtfm.banner.manager;

import com.mtfm.banner.entity.SwiperImage;
import com.mtfm.banner.manager.provisioning.Swiper;
import com.mtfm.core.util.page.Page;
import com.mtfm.core.util.page.PageTemplate;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 轮播图管理
 */
public interface SwiperImageManager {
    /**
     * 创建轮播图
     * @param swiperImage 轮播图
     * @return 轮播图id
     */
    long createSwiperImage(SwiperImage swiperImage);

    /**
     * 修改轮播图
     * @param swiperImage 轮播图
     */
    void updateSwiperImage(SwiperImage swiperImage);

    /**
     * 删除图片
     * @param id 轮播图id
     */
    void removeSwiperImage(long id);

    /**
     * 获取轮播图详情
     * @param id 轮播图id
     * @return 轮播图详情
     */
    SwiperImage loadSwiperDetails(long id);

    /**
     * 分页轮播图
     * @param page 分页
     * @return 分页轮播图详情
     */
    PageTemplate<Swiper> loadSwiperPage(Page page);

    /**
     * 获取所有的轮播图
     * @return 轮播图
     */
    List<Swiper> loadSwiper();
}
