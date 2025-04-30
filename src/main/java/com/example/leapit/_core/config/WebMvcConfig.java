package com.example.leapit._core.config;

import com.example.leapit._core.interceptor.LoginInterceptor;
import com.example.leapit._core.interceptor.RoleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

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

    // 정적 리소스 핸들러 설정 (업로드 이미지 접근용)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/upload/**")             // URL 경로
                .addResourceLocations("file:./upload/")                   // 실제 파일 경로
                .setCachePeriod(60 * 60)                                  // 캐시: 1시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
