package com.mtfm.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = false)
@MapperScan(basePackages = {"com.mtfm.app_support.mapper", "com.mtfm.purchase.manager.mapper", "com.mtfm.banner.mapper"})
public class MidasAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidasAppApplication.class, args);
    }

}
