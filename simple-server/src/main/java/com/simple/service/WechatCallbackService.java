package com.simple.service;
import com.simple.common.utils.Sha1Signature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * description:  <br>
 * date: 2023/5/7 15:42 <br>
 */
@Slf4j
@Service
public class WechatCallbackService {

    public String checkSignature(String signature, String timestamp, String nonce, String echostr) throws Exception {
        log.info("checkSignature: signature:{} timestamp:{} nonce:{} echostr:{}", signature, timestamp, nonce, echostr);
        String token = "checkSignature";
        String sign = Sha1Signature.sign(token, timestamp, nonce);
        if (sign.equalsIgnoreCase(signature)) {
            return echostr;
        }
        return null;
    }
}
