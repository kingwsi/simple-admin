package com.simple.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.handler.AuthFilterHandler;
import com.simple.handler.RestAuthFilterHandler;
import com.simple.service.ApiWhitelistService;
import com.simple.service.ResourceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final ResourceService resourceService;

    private final ApiWhitelistService apiWhitelistService;
    
    private final ObjectMapper objectMapper;

    public WebConfiguration(ResourceService resourceService, ApiWhitelistService apiWhitelistService, ObjectMapper objectMapper) {
        this.resourceService = resourceService;
        this.apiWhitelistService = apiWhitelistService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public FilterRegistrationBean<AuthFilterHandler> authFilter() {
        FilterRegistrationBean<AuthFilterHandler> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new AuthFilterHandler(resourceService, apiWhitelistService));
        filterRegBean.addUrlPatterns("/api/*");
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }

    @Bean
    @ConditionalOnProperty(value="signature.enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<RestAuthFilterHandler> signatureFilter() {
        FilterRegistrationBean<RestAuthFilterHandler> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new RestAuthFilterHandler());
        filterRegBean.addUrlPatterns("/rest/*");
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/web/index.html");
    }
}
