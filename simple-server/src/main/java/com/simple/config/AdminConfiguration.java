package com.simple.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * description: AdminConfiguration <br>
 * date: 2020/9/30 13:09 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Configuration
public class AdminConfiguration {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IKeyGenerator keyGenerator() {
        return new H2KeyGenerator();
    }
}
