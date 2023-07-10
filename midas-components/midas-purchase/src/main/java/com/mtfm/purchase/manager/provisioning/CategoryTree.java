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

import com.mtfm.core.util.Linkable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类树
 */
public class CategoryTree extends CategoryDetails implements Serializable, Linkable<CategoryTree> {
    /**
     * 子节点
     */
    private List<CategoryTree> nodes;
    /**
     * 所处高度
     */
    private int height;

    public CategoryTree() {
        this(new ArrayList<>(), 0);
    }

    public CategoryTree(List<CategoryTree> nodes, int height) {
        this.nodes = nodes;
        this.height = height;
    }

    @Override
    public String getKey() {
        return String.valueOf(this.getTarget());
    }

    @Override
    public String getParent() {
        return String.valueOf(super.getParentId());
    }

    @Override
    public List<CategoryTree> getNodes() {
        return this.nodes;
    }

    @Override
    public void setNodes(List<CategoryTree> nodes) {
        this.nodes = nodes;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public void setKey(String key) {
        super.setTarget(Long.parseLong(key));
    }

    public void setParent(String parent) {
        super.setParentId(Long.parseLong(parent));
    }
}
