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
package com.mtfm.backend_mall;

import com.mtfm.core.CodeExpression;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商城错误码
 */
public enum MallCode implements CodeExpression {
    /**
     * 商品不存在
     */
    SPU_NOT_FOUND(500300, "unable to find the specified product"),
    /**
     * 分类关联有商品，不允许删除
     */
    CATEGORY_NOT_ALLOWED_DELETED(500301, "there are still product associations under the specified " +
            "category for deletion, and deletion is not allowed"),
    /**
     * 分类包含子节点，不允许删除
     */
    CATEGORY_NOT_ALLOWED_DELETE_NODE(500302, "there are child node associations under the specified " +
            "deleted category, and deletion is not allowed"),
    /**
     * 分类已存在
     */
    CATEGORY_NAME_EXIST(500303, "category name has exist or is empty"),
    /**
     * 分类的父级不存在
     */
    CATEGORY_PARENT_NOT_FOUND(500304, "the parent of the added category does not exist"),
    /**
     * 分类不允许添加子节点
     */
    CATEGORY_NOT_ALLOWED_LEVEL(500305, "classification allows up to 3 levels, no more levels are " +
            "allowed to be added"),
    /**
     * 商品规格与预设的规格不匹配
     */
    COMMODITY_SKU_NOT_MATCH(500306, "the product specifications do not match the preset specifications"),
    /**
     * 规格商品不存在
     */
    COMMODITY_NOT_FOUND(500307, "could not found commodity, maybe not exist");

    private final int code;
    private final String message;

    MallCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
