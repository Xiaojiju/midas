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
package com.mtfm.app_purchase.web;

import com.mtfm.banner.manager.SwiperImageManager;
import com.mtfm.banner.manager.SwiperNoticeManager;
import com.mtfm.banner.manager.provisioning.Notice;
import com.mtfm.banner.manager.provisioning.Swiper;
import com.mtfm.core.context.response.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 轮播公告
 */
@RestController
@RequestMapping("/solar/api/v1")
public class SwiperApi {

    private SwiperImageManager swiperImageManager;

    private SwiperNoticeManager swiperNoticeManager;

    public SwiperApi(SwiperImageManager swiperImageManager, SwiperNoticeManager swiperNoticeManager) {
        this.swiperImageManager = swiperImageManager;
        this.swiperNoticeManager = swiperNoticeManager;
    }

    /**
     * 获取轮播图
     * @return 轮播图分组
     */
    @GetMapping("/swiper")
    public RestResult<List<Swiper>> getSwiperImages() {
        return RestResult.success(this.swiperImageManager.loadSwiper());
    }

    /**
     * 获取滚动公告
     * @return 公告
     */
    @GetMapping("/notices")
    public RestResult<List<Notice>> getSwiperNotices() {
        return RestResult.success(this.swiperNoticeManager.loadSwiper());
    }

    protected SwiperImageManager getSwiperImageManager() {
        return swiperImageManager;
    }

    public void setSwiperImageManager(SwiperImageManager swiperImageManager) {
        this.swiperImageManager = swiperImageManager;
    }

    protected SwiperNoticeManager getSwiperNoticeManager() {
        return swiperNoticeManager;
    }

    public void setSwiperNoticeManager(SwiperNoticeManager swiperNoticeManager) {
        this.swiperNoticeManager = swiperNoticeManager;
    }
}
