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
package com.mtfm.backend_mall.web;

import com.mtfm.backend_mall.service.express.ExpressService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.BatchWrapper;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.core.util.validator.ValidateGroup;
import com.mtfm.express.entity.Express;
import com.mtfm.express.manager.provisioning.ExpressItem;
import com.mtfm.express.manager.provisioning.ExpressPageQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 物流信息
 */
@RestController
@RequestMapping("/solar/api/v1")
public class ExpressApi {

    private ExpressService expressService;

    public ExpressApi(ExpressService expressService) {
        this.expressService = expressService;
    }

    @PostMapping("/express")
    public RestResult<Void> createExpress(@RequestBody @Validated({ValidateGroup.Create.class}) Express express) {
        express.setId(null);
        this.expressService.createExpressService(express);
        return RestResult.success();
    }

    @PutMapping("/express")
    public RestResult<Void> updateExpress(@RequestBody @Validated({ValidateGroup.Update.class}) Express express) {
        this.expressService.updateExpressService(express);
        return RestResult.success();
    }

    @GetMapping("/express/{id}")
    public RestResult<Express> getExpressDetails(@PathVariable("id") long id) {
        return RestResult.success(this.expressService.loadByExpressId(id));
    }

    @GetMapping("/expresses")
    public RestResult<PageTemplate<Express>> getPage(ExpressPageQuery query) {
        return RestResult.success(this.expressService.loadPage(query));
    }

    @DeleteMapping("/expresses")
    public RestResult<Void> deleteBatch(@RequestBody @Validated({ValidateGroup.Delete.class}) BatchWrapper<Long> wrapper) {
        this.expressService.removeServiceBatch(wrapper.getTargets());
        return RestResult.success();
    }

    @GetMapping("/express")
    public RestResult<List<ExpressItem>> getItems(String expressService) {
        return RestResult.success(this.expressService.loadExpressByName(expressService));
    }

    protected ExpressService getExpressService() {
        return expressService;
    }

    public void setExpressService(ExpressService expressService) {
        this.expressService = expressService;
    }
}
