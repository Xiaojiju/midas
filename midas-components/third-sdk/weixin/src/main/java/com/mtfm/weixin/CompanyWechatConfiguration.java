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

import com.mtfm.weixin.qy.CompanyWechatSessionService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 企业微信小程序配置
 */
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
