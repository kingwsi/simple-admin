package com.simple;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.simple"})
@MapperScan(basePackages = "com.simple.mapper")
@Slf4j
public class BaseAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseAdminApplication.class, args);
    }
}
