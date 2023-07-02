package com.mtfm.security.config;

import com.mtfm.security.SecurityConstants;
import com.mtfm.security.authentication.AppUserPreAuthenticationChecks;
import com.mtfm.security.cache.RedisUserCache;
import com.mtfm.security.service.NullUserFromJdbcImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = SecurityConstants.CONFIG_PREFIX)
public class WebAutoSecurityConfiguration {
    /**
     * 开启权限认证，默认为开启状态
     */
    private boolean enablePermissions = true;
    /**
     * 允许账号过期
     */
    private boolean enableAccountExpired = true;
    /**
     * 允许密钥过期
     * 若关闭了密钥过期，当密钥过期后，依然会提示过期，但是不会强制用户进行更新
     */
    private boolean enableCredentialsExpired = false;
    /**
     * 允许认证方式过期
     * 若关闭了认证方式过期，当认证方式过期后，依然会提示过期，但是不会强制要求用户更新
     */
    private boolean enableUsernameExpired;
    /**
     * 自定义会话请求头
     */
    private String header;
    /**
     * 过期时间限制
     */
    private Long expiredTimestamp;
    /**
     * 刷新时间限制
     */
    private Long refreshTimestamp;
    /**
     * 是否允许自动刷新会话token
     */
    private boolean enableRefresh = true;

    public WebAutoSecurityConfiguration(ResourceBundleMessageSource resourceBundleMessageSource) {
        resourceBundleMessageSource.addBasenames("org.springframework.security.messages");
        resourceBundleMessageSource.addBasenames("i18n/solar_messages");
    }

    /**
     * provider管理器
     * 自定义构建的所有的provider都会经由这个管理器进行管理，特定的认证需要经由特定的provider执行，需要在实现{@link AuthenticationProvider}
     * 后，重写其中的{@link AuthenticationProvider#supports(Class)}方法来进行区分执行；
     * 需要使用providerManager, 仅仅需要直接引入即可
     * @param providers 对应认证的provider； 将provider加入到{@link AuthenticationManager}仅仅需要将实现类加入到Spring容器中
     * @return provider管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> providers) {
        return new ProviderManager(providers);
    }

    @Bean
    @ConditionalOnMissingBean(DaoAuthenticationProvider.class)
    public AuthenticationProvider authenticationProvider(RedisTemplate<String, String> redisTemplate) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(new NullUserFromJdbcImpl());
        daoAuthenticationProvider.setUserCache(new RedisUserCache(redisTemplate));
        daoAuthenticationProvider.setPreAuthenticationChecks(new AppUserPreAuthenticationChecks(this.enableAccountExpired,
                this.enableUsernameExpired, this.enableCredentialsExpired));
        return daoAuthenticationProvider;
    }

    /**
     * 默认的密钥加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public boolean isEnablePermissions() {
        return enablePermissions;
    }

    public void setEnablePermissions(boolean enablePermissions) {
        this.enablePermissions = enablePermissions;
    }

    public boolean isEnableAccountExpired() {
        return enableAccountExpired;
    }

    public void setEnableAccountExpired(boolean enableAccountExpired) {
        this.enableAccountExpired = enableAccountExpired;
    }

    public boolean isEnableCredentialsExpired() {
        return enableCredentialsExpired;
    }

    public void setEnableCredentialsExpired(boolean enableCredentialsExpired) {
        this.enableCredentialsExpired = enableCredentialsExpired;
    }

    public boolean isEnableUsernameExpired() {
        return enableUsernameExpired;
    }

    public void setEnableUsernameExpired(boolean enableUsernameExpired) {
        this.enableUsernameExpired = enableUsernameExpired;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Long getExpiredTimestamp() {
        return expiredTimestamp;
    }

    public void setExpiredTimestamp(Long expiredTimestamp) {
        this.expiredTimestamp = expiredTimestamp;
    }

    public Long getRefreshTimestamp() {
        return refreshTimestamp;
    }

    public void setRefreshTimestamp(Long refreshTimestamp) {
        this.refreshTimestamp = refreshTimestamp;
    }

    public boolean isEnableRefresh() {
        return enableRefresh;
    }

    public void setEnableRefresh(boolean enableRefresh) {
        this.enableRefresh = enableRefresh;
    }
}
