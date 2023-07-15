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
import com.mtfm.purchase.entity.SpuAttribute;
import com.mtfm.purchase.manager.AttributeManager;
import com.mtfm.purchase.manager.mapper.SpuAttributeMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * spu属性值管理
 */
@Transactional(rollbackFor = Exception.class)
public class SpuAttributeManageService extends ServiceImpl<SpuAttributeMapper, SpuAttribute>
        implements AttributeManager<SpuAttribute> {

    @Override
    public void setAttributes(long id, List<SpuAttribute> attributeGroupValues) {
        // 删除所有属性
        this.removeAttributes(id, null);
        for (SpuAttribute attribute : attributeGroupValues) {
            attribute.setSpuId(id);
        }
        this.saveBatch(attributeGroupValues);
    }

    @Override
    public void removeAttributes(long id, Collection<Long> attributes) {
        QueryWrapper<SpuAttribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SpuAttribute::getSpuId, id)
                .in(!CollectionUtils.isEmpty(attributes), SpuAttribute::getId, attributes);
        this.remove(queryWrapper);
    }

    @Override
    public List<SpuAttribute> loadAttributesById(long id) {
        return this.list(new QueryWrapper<SpuAttribute>().lambda().eq(SpuAttribute::getSpuId, id));
    }
}
