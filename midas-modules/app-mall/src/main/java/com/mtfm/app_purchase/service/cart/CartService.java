package com.mtfm.app_purchase.service.cart;

import com.mtfm.app_purchase.service.provisioning.CartItemView;

import java.util.List;

public interface CartService {

    void addCart(long id);

    void removeItems(List<Long> items);

    void updateQuantity(long id, int quantity);

    List<CartItemView> loadViews();
}
