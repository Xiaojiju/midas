package com.mtfm.weixin.mp.service;

import com.mtfm.tools.HttpHelper;
import com.mtfm.tools.JSONUtils;
import com.mtfm.weixin.WechatMiniProgramConfiguration;
import com.mtfm.weixin.mp.AccessResult;
import com.mtfm.weixin.mp.AccessTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);
    private final RestTemplate restTemplate;
    private final WechatMiniProgramConfiguration property;

    public AccessTokenService(RestTemplate restTemplate, WechatMiniProgramConfiguration property) {
        this.restTemplate = restTemplate;
        this.property = property;
    }

    public AccessResult getAccessToken() throws IllegalAccessException {
        AccessTemplate template = new AccessTemplate();
        template.setAppid(property.getAppId());
        template.setGrant_type(WechatMiniProgramConfiguration.CLIENT_CREDENTIAL);
        template.setSecret(property.getSecretKey());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(HttpHelper.getRequestUrl(WechatMiniProgramConfiguration.ACCESS_TOKEN, template), String.class);
        AccessResult accessResult = JSONUtils.from(forEntity.getBody(), AccessResult.class);
        if (accessResult.success()) {
            return accessResult;
        }
        if (logger.isErrorEnabled()) {
            logger.error("url: {}, error code: {}, error message: {}", WechatMiniProgramConfiguration.ACCESS_TOKEN, accessResult.getErrcode(), accessResult.getErrmsg());
        }
        throw new IllegalAccessException("wechat token error, error code " + accessResult.getErrcode() + ", error message " + accessResult.getErrmsg());
    }
}
