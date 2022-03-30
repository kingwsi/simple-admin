package com.simple.api;

import com.simple.common.bean.AuthUser;
import com.simple.common.bean.ResponseData;
import com.simple.common.entity.user.UserPwdVO;
import com.simple.common.enumerate.RedisConstKey;
import com.simple.service.AccessControlService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 授权相关接口
 * Name: AuthController
 * Author: ws
 * Date: 2019/6/29 15:15
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AccessControlService accessControlService;


    public AuthController(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    @PostMapping
    public ResponseData<String> authWithVerify(@RequestBody AuthUser authUser) {
        String key = RedisConstKey.LOGIN_VERIFY_CODE + authUser.getKey();
//        String code = stringRedisTemplate.opsForValue().get(key);
//        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(authUser.getCaptcha())) {
//            return ResponseData.FAIL("验证码错误");
//        }

        return ResponseData.OK(accessControlService.auth(authUser));
    }
    @PostMapping("/logout")
    public ResponseData<?> logout() {
        return ResponseData.OK();
    }

    @PutMapping("/user")
    public ResponseData<?> updateCurrentUserInfo(@RequestBody @Validated UserPwdVO vo){
        return ResponseData.OK(accessControlService.updateCurrentUserInfo(vo));
    }
}
