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

import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.provisioning.BrandDetails;

import javax.validation.constraints.NotNull;
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
    void createBrand(@NotNull BrandDetails details) throws PurchaseExistException;

    /**
     * 修改品牌
     * @param details 品牌详情
     */
    void updateBrand(@NotNull BrandDetails details) throws PurchaseNotFoundException;

    /**
     * 加载品牌详情
     * @param id 品牌id
     * @return 品牌详情
     */
    BrandDetails loadBrandById(long id);

    /**
     * 加载品牌详情
     * @param brand 品牌名称
     * @return 品牌详情
     */
    BrandDetails loadBrandByName(String brand);

    /**
     * 通过首字母加载品牌详情
     * @param letter 首字母
     * @return 品牌详情集合
     */
    List<BrandDetails> loadBrandByLetter(String letter);

    /**
     * 删除品牌
     * @param id 品牌id
     */
    void removeBrandById(Long id);

    /**
     * 删除品牌
     * @param brand 品牌名
     */
    void removeBrandByName(String brand);

    /**
     * 品牌名是否存在
     * @param brand 品牌名
     * @return true 存在 false 不存在
     */
    boolean brandExist(String brand);
}
