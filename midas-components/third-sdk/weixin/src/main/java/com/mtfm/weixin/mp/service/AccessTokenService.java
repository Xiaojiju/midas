/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.weixin.mp.service;

import com.mtfm.tools.HttpHelper;
import com.mtfm.tools.JSONUtils;
import com.mtfm.weixin.WechatMiniProgramConfiguration;
import com.mtfm.weixin.mp.AccessResult;
import com.mtfm.weixin.mp.AccessTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 小程序接口调用凭据
 */
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
