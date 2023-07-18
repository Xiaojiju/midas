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
package com.mtfm.app_purchase.web.api;

import com.mtfm.app_purchase.service.purchase.CommodityService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommodityView;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.purchase.manager.service.bo.CommodityPageQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品信息api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class CommodityViewApi {

    private CommodityService commodityService;

    public CommodityViewApi(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    /**
     * 分类展示商品，主要集中在逛一逛和特选
     * @param query 查询参数
     * @return 商品简要信息
     */
    @GetMapping("/view/commodities")
    public RestResult<PageTemplate<CommodityView>> getViews(CommodityPageQuery query) {
        return RestResult.success(this.commodityService.loadViewPage(query));
    }

    /**
     * 规格商品信息
     * @param commodityId 商品id
     * @return 商品详情
     */
    @GetMapping("/view/commodity/{commodityId}")
    public RestResult<CommodityDetails> getCommodityDetails(@PathVariable("commodityId") long commodityId) {
        return RestResult.success(this.commodityService.loadDetails(commodityId));
    }

    /**
     * 商品spu信息
     * @param spuId 商品spuId
     * @return spu详情
     */
    @GetMapping("/view/goods/{spuId}")
    public RestResult<SpuDetails> getSpuDetails(@PathVariable("spuId") long spuId) {
        return RestResult.success(this.commodityService.loadSpuDetails(spuId));
    }

    protected CommodityService getCommodityService() {
        return commodityService;
    }

    public void setCommodityService(CommodityService commodityService) {
        this.commodityService = commodityService;
    }
}
