package com.mtfm.app_purchase.service.cart.impl;

import com.mtfm.app_purchase.AppPurchaseMessageSource;
import com.mtfm.app_purchase.MallCode;
import com.mtfm.app_purchase.mapper.CartMapper;
import com.mtfm.app_purchase.service.cart.CartService;
import com.mtfm.app_purchase.service.provisioning.CartItemView;
import com.mtfm.app_purchase.service.purchase.CommodityService;
import com.mtfm.cart.entity.CartItem;
import com.mtfm.cart.manager.CartItemManager;
import com.mtfm.core.ServiceCode;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.purchase.entity.CommodityAttribute;
import com.mtfm.purchase.entity.CommodityImage;
import com.mtfm.purchase.manager.SkuManager;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.purchase.manager.provisioning.SpuDetails;
import com.mtfm.security.SecurityHolder;
import com.mtfm.tools.JSONUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class CartServiceImpl implements CartService, MessageSourceAware {

    private MessageSourceAccessor messages = AppPurchaseMessageSource.getAccessor();

    private final CartMapper cartMapper;

    private CartItemManager cartItemManager;

    public CartServiceImpl(CartMapper cartMapper, CartItemManager cartItemManager) {
        this.cartMapper = cartMapper;
        this.cartItemManager = cartItemManager;
    }

    @Override
    public void addCart(long id) {
        String userId = (String) SecurityHolder.getPrincipal();
        CartItem cartItem = this.cartMapper.selectById(id);
        if (cartItem == null) {
            throw new ServiceException(this.messages.getMessage("CommodityManageService.notFound",
                    "could not found commodity, maybe not exist."),
                    ServiceCode.DATA_NOT_FOUND.getCode());
        }
        cartItem.setUserId(userId);
        cartItem.setQuantity(1);
        this.cartItemManager.addItem(cartItem);
    }

    @Override
    public void removeItems(List<Long> items) {
        String userId = (String) SecurityHolder.getPrincipal();
        this.cartItemManager.removeItems(userId, items);
    }

    @Override
    public void updateQuantity(long id, int quantity) {
        String userId = (String) SecurityHolder.getPrincipal();
        this.cartItemManager.setQuality(id, userId, quantity);
    }

    @Override
    public List<CartItemView> loadViews() {
        return null;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
