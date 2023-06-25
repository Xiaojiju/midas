package com.mtfm.weixin.mp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mtfm.tools.HttpHelper;
import com.mtfm.tools.JSONUtils;
import com.mtfm.weixin.WechatMiniProgramConfiguration;
import com.mtfm.weixin.mp.PhoneResult;
import com.mtfm.weixin.mp.PhoneTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PhoneInfoService {

    private static final Logger logger = LoggerFactory.getLogger(PhoneInfoService.class);
    private final AccessTokenService accessTokenService;
    private final RestTemplate restTemplate;

    public PhoneInfoService(AccessTokenService accessTokenService, RestTemplate restTemplate) {
        this.accessTokenService = accessTokenService;
        this.restTemplate = restTemplate;
    }

    public PhoneResult getPhone(String code) throws IllegalAccessException {
        return getPhone(null, code);
    }

    public PhoneResult getPhone(String accessToken, String code) throws IllegalAccessException {
        if (!StringUtils.hasText(accessToken)) {
            accessToken = accessTokenService.getAccessToken().getAccess_token();
        }
        HttpHeaders headers = new HttpHeaders();
        PhoneTemplate template = new PhoneTemplate(code);
        Map<String, String> variables = JSONUtils.convertValue(template, new TypeReference<>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put(WechatMiniProgramConfiguration.ACCESS_TOKEN_VARIABLE, accessToken);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(variables, headers);
        ResponseEntity<String> forEntity = restTemplate.postForEntity(HttpHelper.getRequestUrl(WechatMiniProgramConfiguration.PHONE, map), entity, String.class);
        PhoneResult phoneResult = JSONUtils.from(forEntity.getBody(), PhoneResult.class);
        if (phoneResult.success()) {
            return phoneResult;
        }
        if (logger.isErrorEnabled()) {
            logger.error("url: {}, error code: {}, error message: {}", WechatMiniProgramConfiguration.PHONE, phoneResult.getErrcode(), phoneResult.getErrmsg());
        }
        throw new IllegalAccessException("wechat mobile data error, error code: " + phoneResult.getErrcode() + " error message: " + phoneResult.getErrmsg());
    }

}
