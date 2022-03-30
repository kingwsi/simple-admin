package com.simple.common.entity.member;

import com.baomidou.mybatisplus.annotation.TableName;
import com.simple.common.entity.common.BaseEntity;
import java.time.LocalDateTime;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* description: 会员 <br>
* date: 2021-04-13 <br>
* author: ws <br>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("member")
public class Member extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String realName;

    private String nickName;

    private String gender;

    private String mobile;

    private String email;

    private String avatar;

    private String introduce;

    private LocalDateTime lastLoginTime;

    private String password;

    private String lastLoginIp;

    private Integer accountStatus;

    private String openid;
}
