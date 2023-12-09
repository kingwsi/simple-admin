package com.simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * description:  <br>
 * date: 2023/12/9 10:21 <br>
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.simple.api"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("rest")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.simple.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}
