package com.mtfm.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity(debug = true)
@MapperScan(basePackages = {
        "com.mtfm.backend_support.provisioning.mapper",
        "com.mtfm.purchase.manager.mapper",
        "com.mtfm.banner.mapper",
        "com.mtfm.express.mapper"
})
public class MidasBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidasBackendApplication.class, args);
    }

}
