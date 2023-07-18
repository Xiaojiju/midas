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
package com.mtfm.backend_mall.web.api;

import com.mtfm.backend_mall.service.purchase.MallCommodityManageService;
import com.mtfm.backend_mall.web.IdTemplate;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.Target;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.core.util.validator.ValidateGroup;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.CommoditySplitDetails;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.purchase.manager.service.bo.SplitPageQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品业务api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class CommodityServiceApi {

    private MallCommodityManageService commodityManageService;

    public CommodityServiceApi(MallCommodityManageService commodityManageService) {
        this.commodityManageService = commodityManageService;
    }

    /**
     * 创建spu
     * @param spuDetails spu详情
     * @return id template
     */
    @PostMapping("/spu")
    public RestResult<IdTemplate> createSpu(@RequestBody @Validated({ValidateGroup.Create.class}) SpuDetails spuDetails) {
        return RestResult.success(new IdTemplate(this.commodityManageService.createSpuDetails(spuDetails)));
    }

    /**
     * 修改spu
     * @param spuDetails spu详情
     */
    @PutMapping("/spu")
    public RestResult<Void> updateSpu(@RequestBody @Validated({ValidateGroup.Update.class}) SpuDetails spuDetails) {
        this.commodityManageService.updateSpuDetails(spuDetails);
        return RestResult.success();
    }

    /**
     * 删除spu
     * @param target spu id
     */
    @DeleteMapping("/spu")
    public RestResult<Void> removeSpu(@RequestBody @Validated({ValidateGroup.Delete.class}) Target<Long> target) {
        this.commodityManageService.removeSpu(target.getTarget());
        return RestResult.success();
    }

    /**
     * 获取商品分页
     * @param query 过滤参数
     */
    @GetMapping("/spu/page")
    public RestResult<PageTemplate<CommoditySplitDetails>> pullPageList(SplitPageQuery query) {
        return RestResult.success(this.commodityManageService.commodityPageList(query));
    }

    /**
     * 拉取spu详情
     * @param spuId spu id
     * @return spu详情
     */
    @GetMapping("/spu/{spuId}")
    public RestResult<SpuDetails> pullSpuDetails(@PathVariable("spuId") long spuId) {
        return RestResult.success(this.commodityManageService.getSpuDetailsById(spuId));
    }

    /**
     * 拉取规格商品
     * @param spuId 商品id
     * @return 商品详情
     */
    @GetMapping("/spu/{spuId}/commodities")
    public RestResult<List<CommodityDetails>> pullCommodities(@PathVariable("spuId") long spuId) {
        return RestResult.success(this.commodityManageService.getCommodityDetailsBySpuId(spuId));
    }

    /**
     * 创建规格商品
     * @param commodityDetails 规格商品详情
     * @return id template
     */
    @PostMapping("/spu/commodity")
    public RestResult<IdTemplate> createCommodity(@RequestBody CommodityDetails commodityDetails) {
        long id = this.commodityManageService.createCommodityDetails(commodityDetails);
        return RestResult.success(new IdTemplate(id));
    }

    /**
     * 修改规格商品
     * @param commodityDetails 规格商品详情
     */
    @PutMapping("/spu/commodity")
    public RestResult<Void> updateCommodity(@RequestBody CommodityDetails commodityDetails) {
        this.commodityManageService.updateCommodityDetails(commodityDetails);
        return RestResult.success();
    }

    /**
     * 删除规格商品
     * @param target 规格商品id
     */
    @DeleteMapping("/spu/commodity")
    public RestResult<Void> removeCommodity(@RequestBody @Validated({ValidateGroup.Delete.class}) Target<Long> target) {
        this.commodityManageService.removeCommodity(target.getTarget());
        return RestResult.success();
    }

    protected MallCommodityManageService getCommodityManageService() {
        return commodityManageService;
    }

    public void setCommodityManageService(MallCommodityManageService commodityManageService) {
        this.commodityManageService = commodityManageService;
    }
}
