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
package com.mtfm.cart.manager.provisioning;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 购物车商品详情定义
 */
public interface CartItemDetails extends Serializable {

    Long getId();

    Long getSpuId();

    Long getCommodityId();

    String getProduct();

    String getTitle();

    String getSubtitle();

    Integer getQuantity();

    String getPreviousMarketPrice();

    String getSkuAttributes();

    String getIndexImage();

    LocalDateTime getCreateTime();
}
