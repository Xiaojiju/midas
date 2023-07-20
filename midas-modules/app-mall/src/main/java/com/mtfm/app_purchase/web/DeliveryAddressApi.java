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
package com.mtfm.app_purchase.web;

import com.mtfm.app_purchase.service.express.DeliveryAddressService;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.Target;
import com.mtfm.core.util.validator.ValidateGroup;
import com.mtfm.express.entity.DeliveryAddress;
import com.mtfm.express.manager.provisioning.AddressDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 收货api
 */
@RestController
@RequestMapping("/solar/api/v1")
public class DeliveryAddressApi {

    private DeliveryAddressService deliveryAddressService;

    public DeliveryAddressApi(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    /**
     * 创建收货地址
     * @param deliveryAddress 收货地址
     */
    @PostMapping("/delivery-address")
    public RestResult<Void> createAddress(@RequestBody @Validated({ValidateGroup.Create.class}) DeliveryAddress deliveryAddress) {
        this.deliveryAddressService.createAddress(deliveryAddress);
        return RestResult.success();
    }

    /**
     * 修改收货地址
     * @param deliveryAddress 收货地址
     */
    @PutMapping("/delivery-address")
    public RestResult<Void> updateAddress(@RequestBody @Validated({ValidateGroup.Update.class}) DeliveryAddress deliveryAddress) {
        this.deliveryAddressService.updateAddress(deliveryAddress);
        return RestResult.success();
    }

    /**
     * 删除收货地址
     * @param target 地址id
     */
    @DeleteMapping("/delivery-address")
    public RestResult<Void> deleteAddress(@RequestBody @Validated({ValidateGroup.Delete.class}) Target<Long> target) {
        this.deliveryAddressService.removeAddress(target.getTarget());
        return RestResult.success();
    }

    /**
     * 获取收货地址
     * @return 收货地址
     */
    @GetMapping("/delivery-addresses")
    public RestResult<List<AddressDetails>> getAddresses() {
        return RestResult.success(this.deliveryAddressService.loadAddresses());
    }

    /**
     * 获取地址详情
     * @param id 收货地址id
     * @return 收货地址详情
     */
    @GetMapping("/delivery-address/{id}")
    public RestResult<AddressDetails> loadAddress(@PathVariable("id") long id) {
        return RestResult.success(this.deliveryAddressService.loadAddress(id));
    }

    protected DeliveryAddressService getDeliveryAddressService() {
        return deliveryAddressService;
    }

    public void setDeliveryAddressService(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }
}
