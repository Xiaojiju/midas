package com.mtfm.examples;

import com.mtfm.core.context.response.RestResult;
import com.mtfm.core.util.BatchWrapper;
import com.mtfm.core.util.Target;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SpringBootApplication
@EnableWebSecurity(debug = false)
@RestController
public class CoreExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreExampleApplication.class, args);
    }

    @GetMapping("")
    public RestResult<String> test(Target<String> test, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        return RestResult.success(userAgent);
    }

    @PostMapping("")
    public RestResult<List<String>> test1(@RequestBody BatchWrapper<String> wrapper) {
        return RestResult.success(wrapper.getTargets());
    }
}
