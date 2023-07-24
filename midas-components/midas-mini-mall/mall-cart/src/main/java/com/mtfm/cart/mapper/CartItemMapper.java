package com.mtfm.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtfm.cart.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {
}
