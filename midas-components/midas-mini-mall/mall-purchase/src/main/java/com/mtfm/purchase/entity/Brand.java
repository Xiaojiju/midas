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
package com.mtfm.purchase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mtfm.datasource.BaseModel;
import com.mtfm.datasource.handler.CommonEnumTypeHandler;
import com.mtfm.tools.StringUtils;
import com.mtfm.tools.enums.Judge;
import org.apache.ibatis.type.JdbcType;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌
 */
@TableName(value = "purchase_brand", autoResultMap = true)
public class Brand extends BaseModel<Brand> {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 品牌
     */
    private String brand;
    /**
     * logo
     */
    private String logo;
    /**
     * 品牌描述
     */
    private String description;
    /**
     * 是否展示
     */
    @TableField(value = "show", jdbcType = JdbcType.INTEGER, typeHandler = CommonEnumTypeHandler.class)
    private Judge show;
    /**
     * 首字母
     */
    @TableField("index_letter")
    private String indexLetter;
    /**
     * 排序
     */
    private Integer sort;

    public Brand() {
    }

    public Brand(Long id, String brand, String logo, String description, Judge show, String indexLetter, Integer sort) {
        this.id = id;
        this.brand = brand;
        this.logo = logo;
        this.description = description;
        this.show = show;
        this.indexLetter = indexLetter;
        this.sort = sort;
    }

    public static BrandBuilder withName(String brand) {
        return new BrandBuilder(brand);
    }

    public static BrandBuilder created(Long id, String brand) {
        return new BrandBuilder(id, brand);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Judge getShow() {
        return show;
    }

    public void setShow(Judge show) {
        this.show = show;
    }

    public String getIndexLetter() {
        return indexLetter;
    }

    public void setIndexLetter(String indexLetter) {
        this.indexLetter = indexLetter;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public static class BrandBuilder {

        private final Long id;
        private final String brand;
        private String logo;
        private String description;
        private Judge show;
        private Integer sort;

        private BrandBuilder(String name) {
            this(null, name);
        }

        private BrandBuilder(Long id, String name) {
            this.id = id;
            this.brand = name;
        }

        public BrandBuilder withLogo(String logo) {
            this.logo = logo;
            return this;
        }

        public BrandBuilder describe(String description) {
            this.description = description;
            return this;
        }

        public BrandBuilder show(boolean show) {
            if (show) {
                this.show = Judge.YES;
            } else {
                this.show = Judge.NO;
            }
            return this;
        }

        public BrandBuilder sort(int sort) {
            this.sort = sort;
            return this;
        }

        public Brand build() {
            if (this.show == null) {
                this.show = Judge.YES;
            }
            return new Brand(this.id, this.brand, this.logo, this.description, this.show,
                    StringUtils.chineseFirstLetter(this.brand), this.sort);
        }
    }
}
