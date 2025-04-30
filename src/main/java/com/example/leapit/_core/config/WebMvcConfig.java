package com.example.leapit._core.config;

import com.example.leapit._core.interceptor.LoginInterceptor;
import com.example.leapit._core.interceptor.RoleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/s/**");

        registry.addInterceptor(new RoleInterceptor())
                .addPathPatterns("/s/personal/**", "/s/api/personal/**")
                .addPathPatterns("/s/company/**", "/s/api/company/**");
    }
}
