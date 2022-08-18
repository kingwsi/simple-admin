package com.simple.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description:   pay config<br>
 * date: 2022/8/13 21:20 <br>
 * author: wangshu <br>
 */
@Component
@ConfigurationProperties(prefix="pay.alipay")
@Data
public class AlipayConfig {
    private String privateKey;

    private String publicKey;

    private String alipayPublicKey;
    
    private String payGateway;
    
    private String appid;
    
    private String notifyUrl;
    
    private String returnUrl;
    
    private String payResultpage;
}
