package com.simple.config;

import com.simple.handler.AuthFilterHandler;
import com.simple.service.ApiWhitelistService;
import com.simple.service.ResourceService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class WebConfiguration {

    private final ResourceService resourceService;

    private final ApiWhitelistService apiWhitelistService;

    public WebConfiguration(ResourceService resourceService, ApiWhitelistService apiWhitelistService) {
        this.resourceService = resourceService;
        this.apiWhitelistService = apiWhitelistService;
    }

    @Bean
    public FilterRegistrationBean<AuthFilterHandler> authFilter() {
        FilterRegistrationBean<AuthFilterHandler> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new AuthFilterHandler(resourceService, apiWhitelistService));
        filterRegBean.addUrlPatterns("/*");
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }


}
