package com.mtfm.purchase.manager.provisioning;

import com.mtfm.core.util.Linkable;

import java.io.Serializable;
import java.util.List;

public class CategoryTree implements Serializable, Linkable<CategoryTree> {

    private String key;

    private String parent;

    private List<CategoryTree> nodes;

    private int height;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getParent() {
        return this.parent;
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
        this.key = key;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
