package com.simple;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.simple"})
@MapperScan(basePackages = "com.simple.mapper")
@EnableFeignClients(basePackages = {"com.simple.feign"})
public class SimpleAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleAdminApplication.class, args);
    }
}
