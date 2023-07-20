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
package com.mtfm.backend_mall.web;

import com.mtfm.banner.entity.SwiperNotice;
import com.mtfm.banner.manager.SwiperNoticeManager;
import com.mtfm.banner.manager.provisioning.Notice;
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
 * 轮播公告
 */
@RestController
@RequestMapping("/solar/api/v1")
public class SwiperNoticeApi {
    
    private SwiperNoticeManager swiperNoticeManager;

    public SwiperNoticeApi(SwiperNoticeManager swiperNoticeManager) {
        this.swiperNoticeManager = swiperNoticeManager;
    }
    
    /**
     * 创建轮播公告
     * @param SwiperNotice 轮播公告信息
     */
    @PostMapping("/notice")
    public RestResult<Void> createNotice(@RequestBody @Validated({ValidateGroup.Create.class}) SwiperNotice SwiperNotice) {
        this.swiperNoticeManager.createSwiperNotice(SwiperNotice);
        return RestResult.success();
    }

    /**
     * 修改轮播公告
     * @param SwiperNotice 轮播公告信息
     */
    @PutMapping("/notice")
    public RestResult<Void> updateNotice(@RequestBody @Validated({ValidateGroup.Update.class}) SwiperNotice SwiperNotice) {
        this.swiperNoticeManager.updateSwiperNotice(SwiperNotice);
        return RestResult.success();
    }

    /**
     * 轮播公告详情
     * @param id 轮播公告id
     * @return 轮播公告详情
     */
    @GetMapping("/notice/{id}")
    public RestResult<SwiperNotice> getNotice(@PathVariable("id") long id) {
        return RestResult.success(this.swiperNoticeManager.loadNoticeDetails(id));
    }

    /**
     * 轮播公告分页
     * @param pageQuery 分页
     * @return 轮播公告分页
     */
    @GetMapping("/notices")
    public RestResult<PageTemplate<Notice>> getPage(PageQuery pageQuery) {
        return RestResult.success(this.swiperNoticeManager.loadNoticePage(pageQuery));
    }

    /**
     * 删除轮播公告
     * @param target 轮播公告id
     */
    @DeleteMapping("/notice")
    public RestResult<Void> removeSwiperNotice(@RequestBody @Validated({ValidateGroup.Delete.class}) Target<Long> target) {
        this.swiperNoticeManager.removeSwiperNotice(target.getTarget());
        return RestResult.success();
    }

    protected SwiperNoticeManager getSwiperNoticeManager() {
        return swiperNoticeManager;
    }

    public void setSwiperNoticeManager(SwiperNoticeManager swiperNoticeManager) {
        this.swiperNoticeManager = swiperNoticeManager;
    }
}
