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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtfm.purchase.entity.BrandCategoryRelation;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.service.bo.BrandPageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌分类管理
 */
@Mapper
public interface BrandCategoryRelationMapper extends BaseMapper<BrandCategoryRelation> {
    /**
     * 判断分类id是否存在
     * @param categories 分类id结合
     * @return 分类id
     */
    List<Long> selectExist(Collection<Long> categories);

    /**
     * 分页过滤
     * @param page 分页限制
     * @param query 过滤条件
     * @return 品牌详情
     */
    Page<BrandDetails> selectBrandPage(Page<BrandDetails> page, @Param("query") BrandPageQuery query);

    /**
     * 查询品牌的关联的分类
     * @param brandId 品牌id
     * @return 分类集合
     */
    List<CategoryDetails> selectCategoryByBrandId(long brandId);
}
