package com.mtfm.backend_mall.web;

import java.io.Serializable;

public class IdTemplate implements Serializable {

    private Long id;

    public IdTemplate() {
    }

    public IdTemplate(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
