package com.simple.common.wechat;

import lombok.Data;

/**
 * description:  <br>
 * date: 2023/5/7 10:04 <br>
 */
@Data
public class WechatUserInfo {
    private String openId;
    private String nickName;
    private int gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
}
