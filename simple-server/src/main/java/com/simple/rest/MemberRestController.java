package com.simple.rest;

import com.simple.common.bean.ResponseData;
import com.simple.common.wechat.WechatAuth;
import com.simple.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: 会员接口 <br>
 * date: 2023/5/7 10:57 <br>
 */
@Api(tags = "会员")
@RestController
@RequestMapping("/rest/member")
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation("微信授权登录")
    @GetMapping("/wechat")
    public ResponseData<?> wechatAuth(String code) {
        return ResponseData.OK(memberService.wechatOauth(code));
    }

    @ApiOperation("微信授权注册绑定")
    @PostMapping("/wechat/info")
    public ResponseData<?> bindInfo(@RequestBody WechatAuth wechatAuth){
        return ResponseData.OK(memberService.bindWechatInfo(wechatAuth));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public ResponseData<?> getMemberInfo(){
        return ResponseData.OK(memberService.getCurrentMemberInfo());
    }
}
