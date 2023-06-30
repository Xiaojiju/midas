package com.mtfm.app.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.mtfm.app_support.mapper")
public class MidasAppSecurityConfiguration {

}
