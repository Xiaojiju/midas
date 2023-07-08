package com.mtfm.kodo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 七牛云配置
 */
@Configuration
@ConfigurationProperties(prefix = "qiniu.kodo")
public class KodoSupportConfiguration {

}
