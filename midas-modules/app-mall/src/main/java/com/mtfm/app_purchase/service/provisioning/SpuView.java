package com.mtfm.app_purchase.service.provisioning;

import com.mtfm.express.manager.provisioning.ExpressSetting;
import com.mtfm.purchase.manager.provisioning.SpuDetails;

import java.io.Serializable;
import java.util.List;

public class SpuView implements Serializable {

    private SpuDetails spuDetails;

    private List<String> tags;

    private ExpressSetting expressSetting;

    public SpuDetails getSpuDetails() {
        return spuDetails;
    }

    public void setSpuDetails(SpuDetails spuDetails) {
        this.spuDetails = spuDetails;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ExpressSetting getExpressSetting() {
        return expressSetting;
    }

    public void setExpressSetting(ExpressSetting expressSetting) {
        this.expressSetting = expressSetting;
    }
}
