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
package com.mtfm.app_purchase.service.purchase;

import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommodityView;
import com.mtfm.purchase.manager.provisioning.Spu;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品业务
 */
public interface CommodityService {
    /**
     * 获取商品的分页浏览
     * @param query 过滤参数
     * @return 商品简要信息
     */
    PageTemplate<CommodityView> loadViewPage(CommodityPageQuery query);

    /**
     * 获取商品详情
     * @param commodityId 商品id
     * @return 规格商品详情
     */
    CommodityDetails loadDetails(long commodityId);

    /**
     * 获取商品spu信息
     * @param spuId 商品spuId
     * @return spu详情
     */
    Spu.SpuDetails loadSpuDetails(long spuId);
}
