package com.mtfm.weixin.qy;

import com.mtfm.core.ResultCode;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.tools.HttpHelper;
import com.mtfm.tools.JSONUtils;
import com.mtfm.weixin.CompanyWechatConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CompanyWechatSessionService {

    private final static String ACCESS_TOKEN ="https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    private final static String CODE2SESSION = "https://qyapi.weixin.qq.com/cgi-bin/miniprogram/jscode2session";
    private final static String GRANT_TYPE = "authorization_code";

    private RedisTemplate<String, String> redisTemplate;
    private CompanyWechatConfiguration companyWechatConfiguration;
    private RestTemplate restTemplate;

    public CompanyWechatSessionService(RedisTemplate<String, String> redisTemplate,
                                       CompanyWechatConfiguration companyWechatConfiguration,
                                       RestTemplate restTemplate) {
        this.redisTemplate = redisTemplate;
        this.companyWechatConfiguration = companyWechatConfiguration;
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        try {
            CompanyToken companyToken = new CompanyToken(companyWechatConfiguration.getCompanyId(), companyWechatConfiguration.getSecret());
            ResponseEntity<String> forEntity = restTemplate.getForEntity(HttpHelper.getRequestUrl(ACCESS_TOKEN, companyToken), String.class);
            AccessTokenResultData data = JSONUtils.from(forEntity.getBody(), AccessTokenResultData.class);
            if (data.getErrCode() == 0) {
                return data.getAccess_token();
            }
            throw new IllegalAccessException("远程请求企业微信access_token失败: " + data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("获取access_token失败", ResultCode.ERROR.getCode());
        }
    }

    public SessionResultData getSession(String jsCode) {
        try {
            String accessToken = getAccessToken();
            SessionParams sessionParams = new SessionParams(accessToken, jsCode, GRANT_TYPE);
            ResponseEntity<String> forEntity = restTemplate.getForEntity(HttpHelper.getRequestUrl(CODE2SESSION, sessionParams), String.class);
            SessionResultData data = JSONUtils.from(forEntity.getBody(), SessionResultData.class);
            if (data.getErrCode() == 0) {
                redisTemplate.boundValueOps(data.getUserid() + ":access_token").set(accessToken);
                redisTemplate.boundValueOps(data.getUserid() + ":session_data").set(JSONUtils.toJsonString(data));
                return data;
            }
            throw new IllegalAccessException("远程请求企业微信session_key失败: " + data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("获取session_key失败", ResultCode.ERROR.getCode());
        }
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CompanyWechatConfiguration getCompanyWechatConfiguration() {
        return companyWechatConfiguration;
    }

    public void setCompanyWechatConfiguration(CompanyWechatConfiguration companyWechatConfiguration) {
        this.companyWechatConfiguration = companyWechatConfiguration;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
