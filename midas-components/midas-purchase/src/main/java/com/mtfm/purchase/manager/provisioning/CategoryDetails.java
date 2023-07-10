package com.mtfm.purchase.manager.provisioning;

import com.mtfm.core.PropertyEntity;
import com.mtfm.tools.enums.Judge;

public class CategoryDetails implements PropertyEntity<Long> {

    private Long target;

    private String category;

    private Long parentId;

    private Integer level;

    private Judge show;

    private String icon;

    @Override
    public Long getTarget() {
        return this.target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Judge getShow() {
        return show;
    }

    public void setShow(Judge show) {
        this.show = show;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
