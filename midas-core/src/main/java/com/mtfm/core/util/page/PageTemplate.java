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

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>分页结果模板</p>
 * <br/>
 * <p>代码中的{@link #setItems(Collection)}方法的代码如下：</p>
 * <pre>
 *     public void setItems(Collection<T> items) {
 *         if (!CollectionUtils.isEmpty(items)) {
 *             this.items = items;
 *         }
 *     }
 * </pre>
 * <p>在不影响代码逻辑的情况，避免null的发生</p>
 * <p>在实例化对象为空的情况下，返回值如下：</p>
 * <pre>
 *     {
 *         "current": 1,
 *         "size": 12,
 *         "total": 0,
 *         "items": []
 *     }
 * </pre>
 * <p>为其他方法调用或者遍历时避免了空指针</p>
 * <p>同时提供了{@link PageTemplateBuilder}类型进行快速构建模板</p>
 * <pre>
 *     public PageTemplateBuilder<T> setItems(Collection<T> items) {
 *             if (!CollectionUtils.isEmpty(items)) {
 *                 this.items = items;
 *             }
 *             return this;
 *         }
 * </pre>
 * <p>同样，在builder中也进行了null的规避</p>
 * @param <T>
 * @author 一块小饼干
 * @since 1.0.0
 */
public class PageTemplate<T extends Serializable> implements PageResult<T> {

    private long current;

    private long size;

    private long total;

    private Collection<T> items;

    public PageTemplate() {
        this(0L, new ArrayList<>());
    }

    public PageTemplate(long total, Collection<T> items) {
        this(Page.CURRENT, Page.SIZE, total, items);
    }

    public PageTemplate(long current, long size, long total, Collection<T> items) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.items = items;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public Collection<T> getItems() {
        return this.items;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setItems(Collection<T> items) {
        if (!CollectionUtils.isEmpty(items)) {
            this.items = items;
        }
    }

    /**
     * 分页模板builder构建器
     * @param <T> 分页段落对象
     * @author 一块小饼干
     */
    public static class PageTemplateBuilder<T extends Serializable> {

        private long current = Page.CURRENT;
        private long size = Page.CURRENT;
        private long total = 0;
        private Collection<T> items = new ArrayList<>();

        public PageTemplate<T> build() {
            PageTemplate<T> pageTemplate = new PageTemplate<>();
            pageTemplate.setCurrent(this.current);
            pageTemplate.setSize(this.size);
            pageTemplate.setTotal(this.total);
            pageTemplate.setItems(this.items);
            return pageTemplate;
        }

        public PageTemplateBuilder<T> setCurrent(long current) {
            this.current = current;
            return this;
        }

        public PageTemplateBuilder<T> setSize(long size) {
            this.size = size;
            return this;
        }

        public PageTemplateBuilder<T> setTotal(long total) {
            this.total = total;
            return this;
        }

        public PageTemplateBuilder<T> setItems(Collection<T> items) {
            if (!CollectionUtils.isEmpty(items)) {
                this.items = items;
            }
            return this;
        }
    }
}
