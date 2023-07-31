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
import com.mtfm.app_purchase.MallCode;
import com.mtfm.app_purchase.mapper.CartMapper;
import com.mtfm.app_purchase.service.cart.CartService;
import com.mtfm.app_purchase.service.provisioning.CartItemView;
import com.mtfm.app_purchase.service.purchase.CommodityService;
import com.mtfm.cart.entity.CartItem;
import com.mtfm.cart.exception.CartItemNotFoundException;
import com.mtfm.cart.manager.CartItemManager;
import com.mtfm.cart.manager.provisioning.CartItemDetails;
import com.mtfm.core.ServiceCode;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.purchase.manager.provisioning.CommodityDetails;
import com.mtfm.security.AppUser;
import com.mtfm.security.SecurityHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    private final CommodityService commodityService;

    public CartServiceImpl(CartMapper cartMapper, CartItemManager cartItemManager, CommodityService commodityService) {
        this.cartMapper = cartMapper;
        this.cartItemManager = cartItemManager;
        this.commodityService = commodityService;
    }

    @Override
    public void addCart(long id) {
        this.insufficient(id);
        AppUser appUser = (AppUser) SecurityHolder.getPrincipal();
        CartItem cartItem = this.cartMapper.selectById(Long.parseLong(appUser.getId()));
        if (cartItem == null) {
            throw new ServiceException(this.messages.getMessage("CommodityManageService.notFound",
                    "could not found commodity, maybe not exist."),
                    ServiceCode.DATA_NOT_FOUND.getCode());
        }
        List<? extends CartItemDetails> cartItemDetails = this.cartItemManager.loadItems(appUser.getId());
        if (CollectionUtils.isEmpty(cartItemDetails)) {
            cartItem.setUserId(appUser.getId());
            cartItem.setQuantity(1);
            this.cartItemManager.addItem(cartItem);
            return ;
        }
        CartItem exist = null;
        for (CartItemDetails details : cartItemDetails) {
            if (details.getCommodityId() == id) {
                exist = (CartItem) details;
                break;
            }
        }
        if (exist == null) {
            cartItem.setUserId(appUser.getId());
            cartItem.setQuantity(1);
            this.cartItemManager.addItem(cartItem);
            return ;
        }
        exist.setQuantity(exist.getQuantity() + 1);
        // 再次确定是否有库存
        this.insufficient(id);
        this.cartItemManager.updateItem(exist);
    }

    @Override
    public void removeItems(List<Long> items) {
        AppUser appUser = (AppUser) SecurityHolder.getPrincipal();
        try {
            List<? extends CartItemDetails> cartItemDetails = this.cartItemManager.loadItems(appUser.getId());
            if (CollectionUtils.isEmpty(cartItemDetails)) {
                return ;
            }
            boolean allMatch = cartItemDetails.stream().allMatch((item) -> ((CartItem) item).getUserId().equals(appUser.getId()));
            if (allMatch) {
                this.cartItemManager.removeItems(items);
                return ;
            }
            throw new ServiceException(this.messages.getMessage("CartItemManager.cartItemWrongOwn",
                    "partial specified data does not exist"), ServiceCode.DELETE_FAIL.getCode());
        } catch (CartItemNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), ServiceCode.DELETE_FAIL.getCode());
        }
    }

    @Override
    public void updateQuantity(long id, int quantity) {
        this.insufficient(id);
        AppUser appUser = (AppUser) SecurityHolder.getPrincipal();
        try {
            List<? extends CartItemDetails> cartItemDetails = this.cartItemManager.loadItems(appUser.getId());
            if (CollectionUtils.isEmpty(cartItemDetails)) {
                throw new ServiceException(this.messages.getMessage("CommodityManageService.notFound",
                        "could not found commodity, maybe not exist."),
                        ServiceCode.DATA_NOT_FOUND.getCode());
            }
            CartItem cartItem = null;
            for (CartItemDetails details : cartItemDetails) {
                if (details.getId() == id) {
                    cartItem = (CartItem) details;
                    break;
                }
            }
            if (cartItem == null) {
                throw new ServiceException(this.messages.getMessage("CommodityManageService.notFound",
                        "could not found commodity, maybe not exist."),
                        ServiceCode.DATA_NOT_FOUND.getCode());
            }
            cartItem.setQuantity(quantity);
            // 再次确认
            this.insufficient(id);
            this.cartItemManager.updateItem(cartItem);
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

    private void insufficient(long id) {
        CommodityDetails commodityDetails = this.commodityService.loadDetails(id);
        if (commodityDetails.getStocks() <= 0) {
            throw new ServiceException(this.messages.getMessage("CartService.InsufficientStocks",
                    "insufficient stock"), MallCode.INSUFFICIENT_STOCKS.getCode());
        }
    }
}
