package com.mtfm.weixin.mp.service;

import com.mtfm.tools.HttpHelper;
import com.mtfm.tools.JSONUtils;
import com.mtfm.weixin.WechatMiniProgramConfiguration;
import com.mtfm.weixin.mp.AuthCode;
import com.mtfm.weixin.mp.SessionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OauthCodeService {

    private static final Logger logger = LoggerFactory.getLogger(OauthCodeService.class);
    private final WechatMiniProgramConfiguration property;
    private final RestTemplate restTemplate;

    public OauthCodeService(WechatMiniProgramConfiguration property, RestTemplate restTemplate) {
        this.property = property;
        this.restTemplate = restTemplate;
    }

    public SessionResult codeToSession(@NotBlank String code) throws IllegalAccessException {
        AuthCode authCode = AuthCode.builder().setJs_code(code)
                .setAppid(property.getAppId())
                .setSecret(property.getSecretKey())
                .setGrant_type(WechatMiniProgramConfiguration.AUTHORIZATION_CODE).build();
        return codeToSession(authCode);
    }

    public SessionResult codeToSession(@NotNull AuthCode authCode) throws IllegalAccessException {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(HttpHelper.getRequestUrl(WechatMiniProgramConfiguration.OAUTH_CODE_TO_SESSION, authCode), String.class);
        SessionResult from = JSONUtils.from(forEntity.getBody(), SessionResult.class);
        if (from.success()) {
            return from;
        }
        if (logger.isErrorEnabled()) {
            logger.error("Wechat get session by oauth code error, error code {},error message {}", from.getErrcode(), from.getErrmsg());
        }
        throw new IllegalAccessException("Wechat get session by oauth code error");
    }
}
