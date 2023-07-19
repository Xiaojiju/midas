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
package com.mtfm.backend_mall.web.api;

import com.mtfm.banner.entity.SwiperImage;
import com.mtfm.banner.manager.SwiperImageManager;
import com.mtfm.banner.manager.provisioning.Swiper;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.Target;
import com.mtfm.core.util.page.PageQuery;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.core.util.validator.ValidateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 轮播图管理
 */
@RestController
@RequestMapping("/solar/api/v1")
public class SwiperImageApi {

    private SwiperImageManager swiperImageManager;

    public SwiperImageApi(SwiperImageManager swiperImageManager) {
        this.swiperImageManager = swiperImageManager;
    }

    /**
     * 创建轮播图
     * @param swiperImage 轮播图信息
     */
    @PostMapping("/swiper")
    public RestResult<Void> createImage(@RequestBody @Validated({ValidateGroup.Create.class}) SwiperImage swiperImage) {
        this.swiperImageManager.createSwiperImage(swiperImage);
        return RestResult.success();
    }

    /**
     * 修改轮播图
     * @param swiperImage 轮播图信息
     */
    @PutMapping("/swiper")
    public RestResult<Void> updateImage(@RequestBody @Validated({ValidateGroup.Update.class}) SwiperImage swiperImage) {
        this.swiperImageManager.updateSwiperImage(swiperImage);
        return RestResult.success();
    }

    /**
     * 轮播图详情
     * @param id 轮播图id
     * @return 轮播图详情
     */
    @GetMapping("/swiper/{id}")
    public RestResult<SwiperImage> getImage(@PathVariable("id") long id) {
        return RestResult.success(this.swiperImageManager.loadSwiperDetails(id));
    }

    /**
     * 轮播图分页
     * @param pageQuery 分页
     * @return 轮播图分页
     */
    @GetMapping("/swiper")
    public RestResult<PageTemplate<Swiper>> getPage(PageQuery pageQuery) {
        return RestResult.success(this.swiperImageManager.loadSwiperPage(pageQuery));
    }

    /**
     * 删除轮播图
     * @param target 轮播图id
     */
    @DeleteMapping("/swiper")
    public RestResult<Void> removeSwiperImage(@RequestBody @Validated({ValidateGroup.Delete.class}) Target<Long> target) {
        this.swiperImageManager.removeSwiperImage(target.getTarget());
        return RestResult.success();
    }

    protected SwiperImageManager getSwiperImageManager() {
        return swiperImageManager;
    }

    public void setSwiperImageManager(SwiperImageManager swiperImageManager) {
        this.swiperImageManager = swiperImageManager;
    }
}
