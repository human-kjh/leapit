package com.example.leapit._core.config;

import com.example.leapit._core.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //registry.addInterceptor(new LoginInterceptor())
    // .addPathPatterns("/s/personal/**")
    // .addPathPatterns("/s/company/**")
    // .addPathPatterns("/s/reply/**")
    // .addPathPatterns("/s/api/**")
    // .addPathPatterns("/s/api/personal/**")
    // .addPathPatterns("/s/api/company/**")
    //.addPathPatterns("/s/community/**")
    // .excludePathPatterns("/community/{id:\\d+}")
    // .excludePathPatterns("/personal/jobposting/**")
    // .excludePathPatterns("/personal/companyinfo/{id:\\d+}");
    // }
}
