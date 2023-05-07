package com.simple.config;

import com.simple.handler.RestAuthFilterHandler;
import com.simple.service.ApiWhitelistService;
import com.simple.service.ResourceService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class RestWebConfiguration {

    private final ResourceService resourceService;

    private final ApiWhitelistService apiWhitelistService;

    public RestWebConfiguration(ResourceService resourceService, ApiWhitelistService apiWhitelistService) {
        this.resourceService = resourceService;
        this.apiWhitelistService = apiWhitelistService;
    }

    @Bean
    public FilterRegistrationBean<RestAuthFilterHandler> restAuthFilter() {
        FilterRegistrationBean<RestAuthFilterHandler> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new RestAuthFilterHandler(resourceService, apiWhitelistService));
        filterRegBean.addUrlPatterns("/rest/*");
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }


}
