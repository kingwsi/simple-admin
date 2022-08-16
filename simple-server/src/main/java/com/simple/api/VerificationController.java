package com.simple.api;

import com.simple.common.bean.ResponseData;
import com.wf.captcha.SpecCaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

/**
 * Description: 授权相关接口
 * Name: AuthController
 * Author: ws
 * Date: 2021/03/23 15:15
 */
@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @GetMapping("/captcha")
    public ResponseData<?> captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(100, 45, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
//        stringRedisTemplate.opsForValue().set(RedisConstKey.LOGIN_VERIFY_CODE + key, verCode, RedisConstKey.LOGIN_VERIFY_CODE.getExpire(), RedisConstKey.LOGIN_VERIFY_CODE.getTimeUnit());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key", key);
        hashMap.put("image", specCaptcha.toBase64());
        return ResponseData.OK(hashMap);
    }
}
