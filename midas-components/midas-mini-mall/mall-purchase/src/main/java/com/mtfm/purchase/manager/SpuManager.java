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
package com.mtfm.purchase.manager;

import com.mtfm.purchase.manager.provisioning.Spu;
import com.mtfm.tools.enums.Judge;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * spu管理
 */
public interface SpuManager {

    boolean spuExist(String product);

    /**
     * 上架
     * @param grounding {@link Judge#YES} 上架 {@link Judge#NO} 下架
     */
    void grounding(long spuId, Judge grounding);
    /**
     * 创建spu
     * @param spuDetails spu详情
     */
    long createSpu(Spu.SpuDetails spuDetails);

    /**
     * 修改spu
     * @param spuDetails spu详情
     */
    void updateSpu(Spu.SpuDetails spuDetails);

    /**
     * 通过spuId加载详情
     * @param spuId spu id
     * @return spu详情
     */
    Spu.SpuDetails loadSpuDetailsById(long spuId);

    /**
     * 删除spu
     * @param spuId spu id
     */
    void removeSpuById(long spuId);

}
