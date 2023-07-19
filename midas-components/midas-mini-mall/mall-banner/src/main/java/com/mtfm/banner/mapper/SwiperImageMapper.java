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
package com.mtfm.banner.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtfm.banner.entity.SwiperImage;
import com.mtfm.banner.manager.provisioning.Swiper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 轮播图管理mapper
 */
@Mapper
public interface SwiperImageMapper extends BaseMapper<SwiperImage> {
    /**
     * 轮播图分页查询
     * @param page 分页
     * @return 轮播图
     */
    Page<Swiper> selectSwiperPage(Page<Swiper> page);

    /**
     * 查询所有的轮播图
     * @return 轮播图
     */
    List<Swiper> selectSwiper();
}
