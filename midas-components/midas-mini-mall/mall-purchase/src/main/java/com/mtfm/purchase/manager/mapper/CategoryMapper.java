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
import com.mtfm.purchase.entity.Category;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类管理
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 通过id或分类名查询分类
     * 如果参数都为空的情况下，会直接返回所有的分类
     * @param id 分类id
     * @param category 分类名
     * @return 分类详情
     */
    List<CategoryDetails> selectCategory(@Param("id") Long id, @Param("category") String category);
}
