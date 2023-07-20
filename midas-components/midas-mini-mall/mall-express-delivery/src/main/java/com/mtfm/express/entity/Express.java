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
import com.mtfm.datasource.BaseModel;
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 物流配置
 */
@TableName(value = "express", autoResultMap = true)
public class Express extends BaseModel<Express> implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("express_service")
    private String expressService;

    @TableField("service_type")
    private String serviceType;

    @TableField("starting_price")
    private String startingPrice;

    @TableField("minimum_load_bearing_capacity")
    private String minimumLoadBearingCapacity;

    @TableField("exceeding_price_per_kilogram")
    private String exceedingPricePerKilogram;

    @TableField(value = "locked", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge locked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpressService() {
        return expressService;
    }

    public void setExpressService(String expressService) {
        this.expressService = expressService;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getMinimumLoadBearingCapacity() {
        return minimumLoadBearingCapacity;
    }

    public void setMinimumLoadBearingCapacity(String minimumLoadBearingCapacity) {
        this.minimumLoadBearingCapacity = minimumLoadBearingCapacity;
    }

    public String getExceedingPricePerKilogram() {
        return exceedingPricePerKilogram;
    }

    public void setExceedingPricePerKilogram(String exceedingPricePerKilogram) {
        this.exceedingPricePerKilogram = exceedingPricePerKilogram;
    }

    public Judge getLocked() {
        return locked;
    }

    public void setLocked(Judge locked) {
        this.locked = locked;
    }
}
