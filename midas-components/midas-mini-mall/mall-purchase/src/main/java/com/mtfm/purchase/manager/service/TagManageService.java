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
package com.mtfm.purchase.manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.purchase.entity.Tag;
import com.mtfm.purchase.manager.TagManager;
import com.mtfm.purchase.manager.mapper.TagMapper;
import com.mtfm.tools.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 标签
 */
@Transactional(rollbackFor = Exception.class)
public class TagManageService extends ServiceImpl<TagMapper, Tag> implements TagManager {

    @Override
    public List<String> loadTags(long spuId) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Tag::getSpuId, spuId)
                .select(Tag::getTagName);
        return this.listObjs(queryWrapper, result -> (String) result);
    }

    @Override
    public void setTags(long spuId, List<String> tags) {
        if (tags == null) {
            return ;
        }
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Tag::getSpuId, spuId);
        this.remove(queryWrapper);
        if (CollectionUtils.isEmpty(tags)) {
            return ;
        }
        List<Tag> prepared = tags.stream()
                .filter(StringUtils::hasText)
                .map(tag -> new Tag(null, tag, spuId))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(prepared)) {
            return ;
        }
        this.saveBatch(prepared);
    }
}
