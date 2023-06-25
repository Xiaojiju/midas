package com.mtfm.weixin;

import com.mtfm.weixin.qy.CompanyWechatSessionService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "wechat.company")
public class CompanyWechatConfiguration {

    private String companyId;
    private String secret;

    @Bean
    public CompanyWechatSessionService companyWechatSessionService(RedisTemplate<String, String> redisTemplate, RestTemplate restTemplate) {
        return new CompanyWechatSessionService(redisTemplate, this, restTemplate);
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
