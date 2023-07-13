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

import com.mtfm.purchase.manager.AttributeManager;
import com.mtfm.purchase.manager.provisioning.AttributeGroupValue;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 属性管理业务实现
 */
@Transactional(rollbackFor = Exception.class)
public class AttributeManageService implements AttributeManager {

    @Override
    public void setSpuAttributes(long spu, List<AttributeGroupValue> attributeGroupValues) {

    }

    @Override
    public void removeSpuAttributes(long spu, long[] attributes) {

    }

    @Override
    public List<AttributeGroupValue> loadAttributesBySpuId(long id) {
        return null;
    }

    @Override
    public void setCommodityAttributes(long commodity, List<AttributeGroupValue> attributeGroupValues) {

    }

    @Override
    public void removeCommodityAttributes(long commodity, long[] attributes) {

    }

    @Override
    public List<AttributeGroupValue> loadAttributesByCommodityId(long id) {
        return null;
    }
}
