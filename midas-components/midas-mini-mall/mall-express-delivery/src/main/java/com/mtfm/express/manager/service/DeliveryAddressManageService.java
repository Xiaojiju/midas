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
package com.mtfm.express.manager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.express.entity.DeliveryAddress;
import com.mtfm.express.manager.DeliveryAddressManager;
import com.mtfm.express.manager.provisioning.AddressDetails;
import com.mtfm.express.mapper.DeliveryAddressMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 收货地址管理
 */
@Transactional(rollbackFor = Exception.class)
public class DeliveryAddressManageService extends ServiceImpl<DeliveryAddressMapper, DeliveryAddress> implements DeliveryAddressManager {

    @Override
    public void createAddress(DeliveryAddress deliveryAddress) {
        this.save(deliveryAddress);
    }

    @Override
    public void updateAddress(DeliveryAddress deliveryAddress) {
        this.updateById(deliveryAddress);
    }

    @Override
    public void removeAddress(long id) {
        this.removeById(id);
    }

    @Override
    public List<AddressDetails> loadAddresses(String userId) {
        return this.baseMapper.selectAddresses(userId);
    }

    @Override
    public DeliveryAddress loadAddress(long id) {
        return this.getById(id);
    }
}
