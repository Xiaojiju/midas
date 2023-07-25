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
package com.mtfm.app_purchase.web;

import com.mtfm.app_purchase.service.cart.CartService;
import com.mtfm.app_purchase.service.provisioning.CartItemView;
import com.mtfm.app_purchase.web.body.Quantity;
import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.BatchWrapper;
import com.mtfm.core.util.Target;
import com.mtfm.core.util.validator.ValidateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 购物车
 */
@RestController
@RequestMapping("/solar/api/v1")
public class CartApi {

    private final CartService cartService;

    public CartApi(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/shopping-cart")
    public RestResult<Void> addCommodity(@RequestBody @Validated({ValidateGroup.Create.class}) Target<Long> target) {
        this.cartService.addCart(target.getTarget());
        return RestResult.success();
    }

    @DeleteMapping("/shopping-cart")
    public RestResult<Void> removeBatch(@RequestBody @Validated({ValidateGroup.Delete.class}) BatchWrapper<Long> batchWrapper) {
        this.cartService.removeItems(batchWrapper.getTargets());
        return RestResult.success();
    }

    @PutMapping("/shopping-cart/quantity")
    public RestResult<Void> setQuantity(@RequestBody @Validated({ValidateGroup.Update.class}) Quantity quantity) {
        this.cartService.updateQuantity(quantity.getId(), quantity.getQuantity());
        return RestResult.success();
    }

    @GetMapping("/shopping-cart")
    public RestResult<List<CartItemView>> getViews() {
        return RestResult.success(this.cartService.loadViews());
    }
}
