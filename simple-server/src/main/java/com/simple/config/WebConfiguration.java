package com.simple.config;

import com.simple.handler.AuthFilterHandler;
import com.simple.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class WebConfiguration {

    @Autowired
    private ResourceService resourceService;

    @Bean
    public FilterRegistrationBean<AuthFilterHandler> authFilter() {
        FilterRegistrationBean<AuthFilterHandler> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new AuthFilterHandler(resourceService));
        filterRegBean.addUrlPatterns("/*");
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }


}
