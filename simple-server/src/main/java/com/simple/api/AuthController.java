package com.simple.api;

import com.simple.common.bean.AuthUser;
import com.simple.common.bean.ResponseData;
import com.simple.common.entity.user.UserPwdVO;
import com.simple.common.enumerate.RedisConstKey;
import com.simple.service.AccessControlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 授权相关接口
 * Name: AuthController
 * Author: ws
 * Date: 2019/6/29 15:15
 */
@Api(tags = "授权登录")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AccessControlService accessControlService;


    public AuthController(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    @ApiOperation("授权登录")
    @PostMapping
    public ResponseData<String> authWithVerify(@RequestBody AuthUser authUser) {
        String key = RedisConstKey.LOGIN_VERIFY_CODE + authUser.getKey();
//        String code = stringRedisTemplate.opsForValue().get(key);
//        if (StringUtils.isEmpty(code) || !code.equalsIgnoreCase(authUser.getCaptcha())) {
//            return ResponseData.FAIL("验证码错误");
//        }
        return ResponseData.OK(accessControlService.auth(authUser));
    }
    @ApiOperation("注销")
    @PostMapping("/logout")
    public ResponseData<?> logout() {
        return ResponseData.OK();
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/user")
    public ResponseData<?> updateCurrentUserInfo(@RequestBody @Validated UserPwdVO vo){
        return ResponseData.OK(accessControlService.updateCurrentUserInfo(vo));
    }
}
