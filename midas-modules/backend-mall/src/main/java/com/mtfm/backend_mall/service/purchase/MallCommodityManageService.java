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
package com.mtfm.backend_mall.service.purchase;

import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommoditySplitDetails;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.purchase.manager.service.bo.SplitPageQuery;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品管理业务
 */
public interface MallCommodityManageService {
    /**
     * 创建spu
     * @param spuDetails spu详情
     * @return id
     */
    long createSpuDetails(SpuDetails spuDetails);

    /**
     * 修改spu
     * @param spuDetails spu详情
     */
    void updateSpuDetails(SpuDetails spuDetails);

    /**
     * 删除spu
     * @param spuId spuId
     */
    void removeSpu(long spuId);

    /**
     * 获取系列id
     * @param spuId 系列id
     * @return 系列详情
     */
    SpuDetails getSpuDetailsById(long spuId);

    /**
     * 上架
     * @param spuId 系列id
     */
    void listing(long spuId, boolean listing);

    /**
     * 获取商品的列表
     * @param query 过滤参数
     * @return 商品分页
     */
    PageTemplate<CommoditySplitDetails> commodityPageList(SplitPageQuery query);

    /**
     * 获取系列商品下的所有商品
     * @param spuId 系列id
     * @return 商品详情
     */
    List<CommodityDetails> getCommodityDetailsBySpuId(long spuId);

    /**
     * 创建商品详情
     * @param commodityDetails 商品
     * @return 规格商品id
     */
    long createCommodityDetails(CommodityDetails commodityDetails);

    /**
     * 修改商品详情
     * @param commodityDetails 商品详情
     */
    void updateCommodityDetails(CommodityDetails commodityDetails);

    /**
     * 删除商品
     * @param commodityId 商品id
     */
    void removeCommodity(long commodityId);
}
