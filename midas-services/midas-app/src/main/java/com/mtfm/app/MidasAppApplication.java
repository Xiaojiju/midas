package com.mtfm.app;

import com.mtfm.purchase.manager.BrandManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
@MapperScan("com.mtfm.app_support.mapper")
public class MidasAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidasAppApplication.class, args);
    }

}
