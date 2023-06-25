package com.mtfm.weixin;

import com.mtfm.weixin.mp.AccessTemplate;
import com.mtfm.weixin.mp.service.AccessTokenService;
import com.mtfm.weixin.mp.service.OauthCodeService;
import com.mtfm.weixin.mp.service.PhoneInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
    public AccessTemplate accessTemplate() {
        return new AccessTemplate();
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
