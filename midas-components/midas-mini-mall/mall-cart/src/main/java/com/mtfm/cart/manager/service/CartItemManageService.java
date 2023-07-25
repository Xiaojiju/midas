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
package com.mtfm.cart.manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.cart.MallCartMessageSource;
import com.mtfm.cart.entity.CartItem;
import com.mtfm.cart.exception.CartItemNotFoundException;
import com.mtfm.cart.manager.CartItemManager;
import com.mtfm.cart.mapper.CartItemMapper;
import com.mtfm.tools.StringUtils;
import com.mtfm.tools.enums.Judge;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 购物车
 */
@Transactional(rollbackFor = Exception.class)
public class CartItemManageService extends ServiceImpl<CartItemMapper, CartItem> implements CartItemManager, MessageSourceAware {

    private MessageSourceAccessor messages = MallCartMessageSource.getAccessor();

    @Override
    public void addItem(CartItem cartItem) {
        this.save(cartItem);
    }

    @Override
    public void setQuality(long id, String userId, int quantity) {
        if (!StringUtils.hasText(userId)) {
            throw new IllegalArgumentException("user id could not be null");
        }
        CartItem cartItem = this.getById(id);
        if (cartItem == null || cartItem.getId() == id) {
            throw new CartItemNotFoundException(this.messages.getMessage("CartItemManager.cartItemNotFound",
                    "Unable to find the specified product from the shopping cart"));
        }
        cartItem.setQuantity(quantity);
        this.updateById(cartItem);
    }

    @Override
    public void removeItems(String userId, List<Long> items) {
        if (CollectionUtils.isEmpty(items)) {
            return ;
        }
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CartItem::getUserId, userId)
                .in(CartItem::getId, items);
        if (!this.remove(queryWrapper)) {
            throw new CartItemNotFoundException(this.messages.getMessage("CartItemManager.cartItemNotFound",
                    "Unable to find the specified product from the shopping cart"));
        }
    }

    @Override
    public List<CartItem> loadItems(String userId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CartItem::getUserId, userId)
                .eq(CartItem::getDeleted, Judge.NO);
        List<CartItem> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
