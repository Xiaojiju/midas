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

import com.mtfm.express.entity.DeliveryAddress;
import com.mtfm.express.manager.provisioning.AddressDetails;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 收货地址管理
 */
public interface DeliveryAddressManager {
    /**
     * 创建收货地址
     * @param deliveryAddress 收货地址
     */
    void createAddress(DeliveryAddress deliveryAddress);

    /**
     * 修改收货地址
     * @param deliveryAddress 收货地址
     */
    void updateAddress(DeliveryAddress deliveryAddress);

    /**
     * 删除收货地址
     * @param id 地址id
     */
    void removeAddress(long id);

    /**
     * 获取收货地址
     * @return 收货地址
     */
    List<AddressDetails> loadAddresses(String userId);

    /**
     * 获取收货详情
     * @param id 收货id
     * @return 收货地址详情
     */
    DeliveryAddress loadAddress(long id);
}
