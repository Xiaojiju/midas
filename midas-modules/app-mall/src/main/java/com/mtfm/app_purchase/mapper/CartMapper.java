package com.mtfm.app_purchase.mapper;

import com.mtfm.app_purchase.service.provisioning.CartItemView;
import com.mtfm.cart.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    /**
     * 查询购物车商品
     * @param commodityId 商品id
     * @return 购物车商品
     */
    CartItem selectById(long commodityId);

    /**
     * 获取购物车全部商品
     * @param userId 用户id
     * @return 购物车商品
     */
    List<CartItemView> selectItems(String userId);
}
