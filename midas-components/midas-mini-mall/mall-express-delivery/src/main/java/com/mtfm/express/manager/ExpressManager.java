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
package com.mtfm.express.manager;

import com.mtfm.core.util.page.Page;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.express.entity.Express;
import com.mtfm.express.manager.provisioning.ExpressItem;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 物流管理
 */
public interface ExpressManager {
    /**
     * 创建物流信息
     * @param express 物流信息
     */
    void createExpressService(Express express);

    /**
     * 修改物流信息
     * @param express 物流信息
     */
    void updateExpressService(Express express);

    /**
     * 批量删除服务
     * @param services 服务id
     */
    void removeServiceBatch(List<Long> services);

    /**
     * 获取快递服务详情
     * @param id 快递服务id
     * @return 物流信息
     */
    Express loadByExpressId(long id);

    /**
     * 物流分页
     * @param page 分页
     * @return 物流分页信息
     */
    PageTemplate<Express> loadPage(Page page);

    /**
     * 通过名称过滤快递服务
     * @param expressService 物流名称
     * @return 物流简要信息
     */
    List<ExpressItem> loadExpressByName(String expressService);
}
