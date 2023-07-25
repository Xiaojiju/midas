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
package com.mtfm.app_purchase.service.express.impl;

import com.mtfm.app_purchase.MallCode;
import com.mtfm.app_purchase.service.express.DeliveryAddressService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.express.entity.DeliveryAddress;
import com.mtfm.express.manager.DeliveryAddressManager;
import com.mtfm.express.manager.provisioning.AddressDetails;
import com.mtfm.security.SecurityHolder;
import com.mtfm.tools.enums.Judge;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.CollectionUtils;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 收货地址业务
 */
public class DeliveryAddressServiceImpl implements DeliveryAddressService, MessageSourceAware {

    private DeliveryAddressManager deliveryAddressManager;

    private MessageSourceAccessor messages;

    public DeliveryAddressServiceImpl(DeliveryAddressManager deliveryAddressManager) {
        this.deliveryAddressManager = deliveryAddressManager;
    }

    @Override
    public void createAddress(DeliveryAddress deliveryAddress) {
        // 如果没有设定默认收货地址，则默不是默认收货地址
        if (deliveryAddress.getDefaultIndex() != null) {
            deliveryAddress.setDefaultIndex(Judge.NO);
        }
        String userId = (String) SecurityHolder.getPrincipal();
        deliveryAddress.setUserId(userId);

        boolean primary = deliveryAddress.getDefaultIndex() == Judge.YES;
        if (primary) {
            List<AddressDetails> addresses = this.deliveryAddressManager.loadAddresses(userId);
            if (CollectionUtils.isEmpty(addresses)) {
                this.deliveryAddressManager.createAddress(deliveryAddress);
                return ;
            }
            for (AddressDetails address : addresses) {
                if (address.getDefaultIndex() == Judge.YES) {
                    DeliveryAddress delivery = new DeliveryAddress();
                    delivery.setId(address.getId());
                    delivery.setDefaultIndex(Judge.NO);
                    this.deliveryAddressManager.updateAddress(delivery);
                }
            }
        }
        this.deliveryAddressManager.createAddress(deliveryAddress);
    }

    @Override
    public void updateAddress(DeliveryAddress deliveryAddress) {
        DeliveryAddress address = this.deliveryAddressManager.loadAddress(deliveryAddress.getId());
        if (address == null) {
            throw new ServiceException(this.messages.getMessage("DeliveryAddressService.addressNotFound",
                    "the specified shipping address does not exist"),
                    MallCode.DELIVERY_ADDRESS_NOT_FOUND.getCode());
        }
        String userId = (String) SecurityHolder.getPrincipal();
        if (!userId.equals(address.getUserId())) {
            throw new ServiceException(this.messages.getMessage("DeliveryAddressService.addressNotFound",
                    "the specified shipping address does not exist"),
                    MallCode.DELIVERY_ADDRESS_NOT_FOUND.getCode());
        }
        if (deliveryAddress.getDefaultIndex() == Judge.YES) {
            List<AddressDetails> addresses = this.deliveryAddressManager.loadAddresses(userId);
            for (AddressDetails details : addresses) {
                if (details.getDefaultIndex() == Judge.YES && !details.getId().equals(deliveryAddress.getId())) {
                    DeliveryAddress delivery = new DeliveryAddress();
                    delivery.setId(details.getId());
                    delivery.setDefaultIndex(Judge.NO);
                    this.deliveryAddressManager.updateAddress(delivery);
                }
            }
        }
        this.deliveryAddressManager.updateAddress(deliveryAddress);
    }

    @Override
    public void removeAddress(long id) {
        String userId = (String) SecurityHolder.getPrincipal();
        DeliveryAddress deliveryAddress = this.deliveryAddressManager.loadAddress(id);
        if (userId.equals(deliveryAddress.getUserId())) {
            this.deliveryAddressManager.removeAddress(id);
            return ;
        }
        throw new ServiceException(this.messages.getMessage("DeliveryAddressService.addressNotFound",
                "the specified shipping address does not exist"),
                MallCode.DELIVERY_ADDRESS_NOT_FOUND.getCode());
    }

    @Override
    public List<AddressDetails> loadAddresses() {
        String userId = (String) SecurityHolder.getPrincipal();
        return this.deliveryAddressManager.loadAddresses(userId);
    }

    @Override
    public AddressDetails loadAddress(long id) {
        DeliveryAddress deliveryAddress = this.deliveryAddressManager.loadAddress(id);
        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setId(deliveryAddress.getId());
        addressDetails.setAlias(deliveryAddress.getAlias());
        addressDetails.setConsignee(deliveryAddress.getConsignee());
        addressDetails.setPhoneNumber(deliveryAddress.getPhoneNumber());
        addressDetails.setCountry(deliveryAddress.getCountry());
        addressDetails.setProvince(deliveryAddress.getProvince());
        addressDetails.setCity(deliveryAddress.getCity());
        addressDetails.setRegion(deliveryAddress.getRegion());
        addressDetails.setAddress(deliveryAddress.getAddress());
        addressDetails.setDefaultIndex(deliveryAddress.getDefaultIndex());
        return addressDetails;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected DeliveryAddressManager getDeliveryAddressManager() {
        return deliveryAddressManager;
    }

    public void setDeliveryAddressManager(DeliveryAddressManager deliveryAddressManager) {
        this.deliveryAddressManager = deliveryAddressManager;
    }
}
