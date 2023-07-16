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

import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommoditySplitDetails;
import com.mtfm.purchase.manager.provisioning.CommodityView;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;
import com.mtfm.purchase.manager.service.bo.SplitPageQuery;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品管理
 */
public interface CommodityManager {
    /**
     * 创建商品
     * @param details 商品详情
     */
    long createCommodity(CommodityDetails details);

    /**
     * 更新商品
     * @param details 商品详情
     */
    void updateCommodity(CommodityDetails details);

    /**
     * 删除商品
     * @param id 商品id
     */
    void deleteByCommodityId(long id);

    /**
     * 系列列表
     * @param query 过滤参数
     * @return 商品系列简要信息，包含库存
     */
    PageTemplate<CommoditySplitDetails> loadPage(SplitPageQuery query);

    /**
     * 加载系列下所有规格商品
     * @param spuId spu id
     * @return 商品详情
     */
    List<CommodityDetails> loadCommodities(long spuId);

    /**
     * 加载所有规格商品的简要详情
     * @param query 过滤参数
     * @return 商品简要详情
     */
    PageTemplate<CommodityView> loadCommodityViewPage(CommodityPageQuery query);

    /**
     * 查询对应商品详情
     * @param commodityId 商品id
     * @return 商品详情
     */
    CommodityDetails loadCommodityById(long commodityId);
}
