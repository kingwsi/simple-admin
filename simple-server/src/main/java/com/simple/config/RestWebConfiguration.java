package com.simple.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.handler.RestAuthFilterHandler;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class RestWebConfiguration {

    private final ObjectMapper objectMapper;

    public RestWebConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public FilterRegistrationBean<RestAuthFilterHandler> restAuthFilter() {
        FilterRegistrationBean<RestAuthFilterHandler> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new RestAuthFilterHandler(objectMapper));
        filterRegBean.addUrlPatterns("/rest/*");
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }


}
