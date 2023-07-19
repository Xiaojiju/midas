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
package com.mtfm.promotion.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mtfm.datasource.BaseModel;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 限时抢购
 */
@TableName(value = "promotion_flash_sale", autoResultMap = true)
public class FlashSale extends BaseModel<FlashSale> {
}
