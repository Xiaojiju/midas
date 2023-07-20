package com.mtfm.app_purchase.service.express.impl;

import com.mtfm.app_purchase.MallCode;
import com.mtfm.app_purchase.service.express.DeliveryAddressService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.express.entity.DeliveryAddress;
import com.mtfm.express.manager.DeliveryAddressManager;
import com.mtfm.express.manager.provisioning.AddressDetails;
import com.mtfm.security.SecurityHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;

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
        String userId = (String) SecurityHolder.getPrincipal();
        deliveryAddress.setUserId(userId);
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
