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
package com.mtfm.app_purchase.service.cart;

import com.mtfm.app_purchase.service.provisioning.CartItemView;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 *
 */
public interface CartService {
    /**
     * 添加商品到购物车
     * @param id 商品id
     */
    void addCart(long id);

    /**
     * 购物车删除商品
     * @param items 商品
     */
    void removeItems(List<Long> items);

    /**
     * 添加商品数量
     * @param id 商品id
     * @param quantity 数量
     */
    void updateQuantity(long id, int quantity);

    /**
     * 获取购物车的商品
     * @return 商品
     */
    List<CartItemView> loadViews();
}
