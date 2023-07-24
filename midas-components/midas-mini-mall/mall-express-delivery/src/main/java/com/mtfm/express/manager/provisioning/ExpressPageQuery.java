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
package com.mtfm.express.manager.provisioning;

import com.mtfm.core.util.page.PageQuery;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 物流分页查询过滤参数
 */
public class ExpressPageQuery extends PageQuery implements Serializable {

    private String expressService;

    private LocalDateTime updateTime;

    public String getExpressService() {
        return expressService;
    }

    public void setExpressService(String expressService) {
        this.expressService = expressService;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
