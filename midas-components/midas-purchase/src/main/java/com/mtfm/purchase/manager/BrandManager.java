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
package com.mtfm.purchase.manager;

import com.mtfm.core.util.page.Page;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.provisioning.BrandDetails;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌管理
 */
public interface BrandManager {

    /**
     * 创建品牌
     * @param details 品牌详情
     */
    void createBrand(BrandDetails details) throws PurchaseExistException;

    /**
     * 修改品牌
     * @param details 品牌详情
     */
    void updateBrand(BrandDetails details) throws PurchaseNotFoundException;

    /**
     * 加载品牌详情
     * id和brand可以任选，也可以同时使用
     * @param id 品牌id
     * @param brand 品牌名称
     * @return 品牌详情
     */
    BrandDetails loadBrand(Long id, String brand);

    /**
     * 通过首字母加载品牌详情
     * @param letter 首字母
     * @return 品牌详情集合
     */
    List<BrandDetails> loadBrandByLetter(String letter);

    /**
     * 删除品牌
     * id和brand可以任选，也可以同时使用
     * @param id 品牌id
     * @param brand 品牌名
     */
    void removeBrand(Long id, String brand);

    /**
     * 分页品牌
     * @param page 分页参数
     * @return 品牌详情
     */
    PageTemplate<BrandDetails> page(Page page);
}
