package com.mtfm.app_purchase.web.body;

import com.mtfm.core.util.validator.ValidateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Quantity implements Serializable {

    @NotNull(groups = ValidateGroup.Update.class, message = "")
    private Long id;

    @NotNull(groups = ValidateGroup.Update.class, message = "")
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
