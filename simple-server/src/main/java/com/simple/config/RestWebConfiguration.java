package com.simple.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(value="signature.enabled", havingValue = "true", matchIfMissing = true)
public class RestWebConfiguration implements WebMvcConfigurer {
    
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignatureInterceptor(objectMapper))
                .addPathPatterns("/rest/**").order(Ordered.HIGHEST_PRECEDENCE); // 对所有请求应用这个拦截器
    }

}
