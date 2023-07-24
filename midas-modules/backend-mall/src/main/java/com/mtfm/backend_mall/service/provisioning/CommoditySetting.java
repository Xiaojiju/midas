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
package com.mtfm.backend_mall.service.provisioning;

import com.mtfm.express.manager.provisioning.ExpressSetting;
import com.mtfm.tools.enums.Judge;

import java.io.Serializable;
import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 服务配置
 */
public class CommoditySetting implements Serializable {
    /**
     * spu id
     */
    private Long spuId;
    /**
     * 标签
     */
    private List<String> tags;
    /**
     * 物流设定
     */
    private ExpressSetting expressSetting;
    /**
     * 上架状态
     */
    private Judge listing;

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ExpressSetting getExpressSetting() {
        return expressSetting;
    }

    public void setExpressSetting(ExpressSetting expressSetting) {
        this.expressSetting = expressSetting;
    }

    public Judge getListing() {
        return listing;
    }

    public void setListing(Judge listing) {
        this.listing = listing;
    }
}
