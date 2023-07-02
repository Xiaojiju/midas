package com.mtfm.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity(debug = true)
@MapperScan("com.mtfm.backend_support.service.mapper")
public class MidasBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidasBackendApplication.class, args);
    }

}
