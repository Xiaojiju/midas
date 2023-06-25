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
package com.mtfm.weixin;

import com.mtfm.weixin.mp.service.AccessTokenService;
import com.mtfm.weixin.mp.service.OauthCodeService;
import com.mtfm.weixin.mp.service.PhoneInfoService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 微信小程序配置
 */
@Configuration
@ConfigurationProperties(prefix = "wechat.mini-program")
public class WechatMiniProgramConfiguration {
    /**
     * auth code to session
     */
    public static final String OAUTH_CODE_TO_SESSION = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String PHONE = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String CLIENT_CREDENTIAL = "client_credential";
    public static final String ACCESS_TOKEN_VARIABLE = "access_token";

    private String secretKey;
    private String appId;

    @Bean
    public AccessTokenService accessTokenService(RestTemplate restTemplate) {
        return new AccessTokenService(restTemplate, this);
    }

    @Bean
    public OauthCodeService oauthCodeService(RestTemplate restTemplate) {
        return new OauthCodeService(this, restTemplate);
    }

    @Bean
    public PhoneInfoService phoneInfoService(AccessTokenService accessTokenService, RestTemplate restTemplate) {
        return new PhoneInfoService(accessTokenService, restTemplate);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
