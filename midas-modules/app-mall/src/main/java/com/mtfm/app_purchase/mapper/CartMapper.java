package com.mtfm.app_purchase.mapper;

import com.mtfm.app_purchase.service.provisioning.CartItemView;
import com.mtfm.cart.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    CartItem selectById(long commodityId);

//    List<CartItemView> selectItems(long userId);
}
