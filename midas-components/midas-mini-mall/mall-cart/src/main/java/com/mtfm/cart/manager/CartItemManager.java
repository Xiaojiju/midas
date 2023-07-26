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
package com.mtfm.cart.manager;

import com.mtfm.cart.entity.CartItem;
import com.mtfm.cart.manager.provisioning.CartItemDetails;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 购物车管理
 */
public interface CartItemManager {
    /**
     * 添加商品到购物车中
     * @param cartItem 商品
     */
    void addItem(CartItem cartItem);

    /**
     * 修复商品到购物车中
     * @param cartItem 商品
     */
    void updateItem(CartItem cartItem);

    /**
     * 删除购物车商品
     * @param items 购物车商品id
     */
    void removeItems(List<Long> items);

    /**
     * 获取购物车商品
     * @param userId 用户id
     * @return 购物车商品
     */
    List<? extends CartItemDetails> loadItems(String userId);
}
