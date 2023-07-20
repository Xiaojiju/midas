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
     *
     * @param id
     * @return
     */
    DeliveryAddress loadAddress(long id);
}
