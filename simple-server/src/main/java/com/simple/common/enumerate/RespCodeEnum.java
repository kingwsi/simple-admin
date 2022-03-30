package com.simple.common.enumerate;

import lombok.Getter;

/**
 * description: AuthCode <br>
 * date: 2020/9/30 13:40 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Getter
public enum RespCodeEnum {

    USER_DISABLE("当前用户已禁用"),
    AUTH_FAILED("无效的账号或密码"),
    MEMBER_UNBIND("510", "微信授权信息过期");

    private String code;

    private int httpStatus;

    private String description;

    RespCodeEnum(String description) {
        this.code = "500";
        this.httpStatus = 500;
        this.description = description;
    }

    RespCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    RespCodeEnum(String code, int httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
