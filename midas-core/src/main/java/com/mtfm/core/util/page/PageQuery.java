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
package com.mtfm.core.util.page;

import org.springframework.util.StringUtils;

/**
 * <p>实现分页查询的基础规范，其中分页默认当前页码为1，默认大小为12{@link com.dire.util.page.Page}
 * 继承该类的同时，需要结合整体的业务，构建较为通用的查询条件类，防止继承类的膨胀，达到难以维护的地步；
 * @author 一块小饼干
 * @since 1.0.0
 */
public class PageQuery implements Page {

    private static final String ORDER_BY_ASC = "asc";

    private static final String ORDER_BY_DESC = "desc";

    private long current = Page.CURRENT;
    private long size = Page.SIZE;
    private String order;
    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    public boolean usingOrder() {
        return StringUtils.hasText(this.order);
    }

    public boolean isAsc() {
        return ORDER_BY_ASC.equals(this.order);
    }

    public boolean isDesc() {
        return ORDER_BY_DESC.equals(this.order);
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
