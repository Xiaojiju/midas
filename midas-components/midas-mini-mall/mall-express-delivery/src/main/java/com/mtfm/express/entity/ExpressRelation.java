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
package com.mtfm.express.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.express.enums.PostageType;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 绑定商品物流信息
 */
@TableName(value = "express_relation", autoResultMap = true)
public class ExpressRelation implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("spu_id")
    private Long spuId;
    /**
     * 关联快递服务
     */
    @TableField("express_id")
    private Long expressId;
    /**
     * 邮费模式
     */
    @TableField("postage_type")
    private PostageType postageType;
    /**
     * 固定邮费
     */
    private String postage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public PostageType getPostageType() {
        return postageType;
    }

    public void setPostageType(PostageType postageType) {
        this.postageType = postageType;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }
}
