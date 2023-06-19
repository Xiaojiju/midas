package com.mtfm.backend_support.service.query;

import com.mtfm.core.util.page.PageQuery;

public class ValuePageQuery extends PageQuery {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
