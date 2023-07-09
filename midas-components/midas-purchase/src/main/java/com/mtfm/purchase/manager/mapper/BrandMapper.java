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
package com.mtfm.purchase.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtfm.purchase.entity.Brand;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌管理
 */
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {
    /**
     * 查询品牌详情
     * @param id 品牌id
     * @param brand 品牌名
     * @param letter 首字母
     * @return 品牌详情
     */
    List<BrandDetails> selectBrand(@Param("id") Long id, @Param("brand") String brand, @Param("letter") String letter);

    /**
     * 查询品牌分页
     * @param page 分页
     * @param brand 品牌名
     * @param letter 首字母
     * @return 品牌详情
     */
    Page<BrandDetails> selectBrandPage(Page<BrandDetails> page, @Param("brand") String brand, @Param("letter") String letter);

    /**
     * 查询品牌数量
     * @param brand 品牌名
     * @param letter 首字母
     * @return 数量
     */
    int selectBrandCount(@Param("brand") String brand, @Param("letter") String letter);
}
