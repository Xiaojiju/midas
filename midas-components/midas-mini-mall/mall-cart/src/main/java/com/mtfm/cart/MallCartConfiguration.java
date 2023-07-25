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
package com.mtfm.cart;

import com.mtfm.cart.manager.CartItemManager;
import com.mtfm.cart.manager.service.CartItemManageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 购物车配置文件
 */
@Configuration
public class MallCartConfiguration {

    public MallCartConfiguration(ResourceBundleMessageSource messageSource) {
        messageSource.addBasenames("i18n/mall_cart_messages");
    }

    @Bean
    public CartItemManager cartItemManager() {
        return new CartItemManageService();
    }
}
