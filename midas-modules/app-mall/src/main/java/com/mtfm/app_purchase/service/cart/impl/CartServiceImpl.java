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
package com.mtfm.app_purchase.service.cart.impl;

import com.mtfm.app_purchase.AppPurchaseMessageSource;
import com.mtfm.app_purchase.mapper.CartMapper;
import com.mtfm.app_purchase.service.cart.CartService;
import com.mtfm.app_purchase.service.provisioning.CartItemView;
import com.mtfm.cart.entity.CartItem;
import com.mtfm.cart.exception.CartItemNotFoundException;
import com.mtfm.cart.manager.CartItemManager;
import com.mtfm.core.ServiceCode;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.security.SecurityHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 购物车
 */
@Transactional(rollbackFor = Exception.class)
public class CartServiceImpl implements CartService, MessageSourceAware {

    private MessageSourceAccessor messages = AppPurchaseMessageSource.getAccessor();

    private final CartMapper cartMapper;

    private final CartItemManager cartItemManager;

    public CartServiceImpl(CartMapper cartMapper, CartItemManager cartItemManager) {
        this.cartMapper = cartMapper;
        this.cartItemManager = cartItemManager;
    }

    @Override
    public void addCart(long id) {
        // String userId = (String) SecurityHolder.getPrincipal();
        String userId = "1";
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
        // String userId = (String) SecurityHolder.getPrincipal();
        String userId = "1";
        try {
            this.cartItemManager.removeItems(userId, items);
        } catch (CartItemNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), ServiceCode.DELETE_FAIL.getCode());
        }
    }

    @Override
    public void updateQuantity(long id, int quantity) {
        // String userId = (String) SecurityHolder.getPrincipal();
        String userId = "1";
        try {
            this.cartItemManager.setQuality(id, userId, quantity);
        } catch (CartItemNotFoundException notFound) {
            throw new ServiceException(this.messages.getMessage("CommodityManageService.notFound",
                    "could not found commodity, maybe not exist."),
                    ServiceCode.DATA_NOT_FOUND.getCode());
        }

    }

    // 如果是分布式，则该方法则不合适，需要修改 #signle server mode
    @Override
    public List<CartItemView> loadViews() {
        // String userId = (String) SecurityHolder.getPrincipal();
        String userId = "1";
        return this.cartMapper.selectItems(userId);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
