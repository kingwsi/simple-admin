package com.simple.remote;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:  <br>
 * date: 2022/8/18 15:42 <br>
 * author: ws <br>
 */
@FeignClient(name = "wechat-oauth2", url = "https://api.weixin.qq.com")
public interface WechatFeignClient {

    @GetMapping(value = "/sns/oauth2/access_token")
    Response getAccessToken(@RequestParam("appid") String appid, @RequestParam("code") String code, @RequestParam(value = "grant_type", defaultValue = "authorization_code") String grantType, @RequestParam("secret") String secret);

    @GetMapping("/sns/oauth2/refresh_token")
    Response refresh(@RequestParam("appid") String appid, @RequestParam("refresh_token") String refreshToken, @RequestParam(value = "grant_type", defaultValue = "refresh_token") String grantType);
    
    @GetMapping("/sns/userinfo")
    Response getUserInfo(@RequestParam("access_token") String accessToken, @RequestParam("openid") String openid, @RequestParam(value = "lang", defaultValue = "zh_CN") String lang);
}
