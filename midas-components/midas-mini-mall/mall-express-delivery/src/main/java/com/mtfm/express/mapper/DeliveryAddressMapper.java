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
package com.mtfm.express.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.express.entity.DeliveryAddress;
import com.mtfm.express.manager.provisioning.AddressDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 收货地址管理
 */
@Mapper
public interface DeliveryAddressMapper extends BaseMapper<DeliveryAddress> {
    /**
     * 查询用户的物流地址
     * @param userId 用户id
     * @return 物流地址
     */
    List<AddressDetails> selectAddresses(String userId);

    /**
     * 查询用户的物流地址
     * @param id 收货地址id
     * @return 收货地址详情
     */
    AddressDetails selectAddress(long id);
}
