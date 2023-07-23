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
package com.mtfm.express.manager;

import com.mtfm.express.manager.provisioning.ExpressSetting;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品物流信息
 */
public interface ExpressRelationManager {
    /**
     * 商品关联物流信息
     * @param spuId 商品id
     * @param expressSetting 物流信息
     */
    void setRelation(long spuId, ExpressSetting expressSetting);

    /**
     * 商品的物流信息
     * @param spuId 商品id
     * @return 商品设定
     */
    ExpressSetting loadSetting(long spuId);
}
