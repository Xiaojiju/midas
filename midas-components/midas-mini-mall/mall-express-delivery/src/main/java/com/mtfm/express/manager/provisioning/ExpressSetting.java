package com.mtfm.express.manager.provisioning;

import com.mtfm.express.enums.PostageType;

import java.io.Serializable;

public class ExpressSetting implements Serializable {
    /**
     * 邮费类型
     */
    private PostageType postageType;
    /**
     * 固定邮费价格
     */
    private String postage;
    /**
     * 绑定快递公司
     */
    private Long expressId;

    private String expressService;

    private String serviceType;

    public PostageType getPostageType() {
        return postageType;
    }

    public void setPostageType(PostageType postageType) {
        this.postageType = postageType;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public String getExpressService() {
        return expressService;
    }

    public void setExpressService(String expressService) {
        this.expressService = expressService;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}