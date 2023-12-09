package com.simple.common.bean;

import com.simple.common.enumerate.AuthType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * description: User <br>
 * date: 2020/9/29 13:23 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Data
@NoArgsConstructor
public class AuthUser implements Serializable {
    private Integer id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String mobile;
    private AuthType authType;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("验证码")
    private String captcha;
    @ApiModelProperty("验证码key")
    private String key;
    private List<String> roles;

    public AuthUser(Integer id, String username, AuthType type) {
        this.id = id;
        this.username = username;
        this.authType = type;
    }
}
