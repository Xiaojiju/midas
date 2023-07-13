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
package com.mtfm.purchase.manager.provisioning;

import java.io.Serializable;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 属性分组
 */
public class AttributeGroupValue {

    private long id;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 分组属性
     */
    private List<AttributeValue> attributeValues;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    /**
     * @author 一块小饼干
     * @since 1.0.0
     * 属性
     */
    public static class AttributeValue implements Serializable {

        /**
         * 属性id
         */
        private Long id;
        /**
         * 属性名
         */
        private String attribute;
        /**
         * 属性值
         */
        private String value;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
