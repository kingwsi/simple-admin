package com.simple.rest;

import com.simple.common.bean.ResponseData;
import com.simple.common.wechat.WechatAuth;
import com.simple.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: 会员接口 <br>
 * date: 2023/5/7 10:57 <br>
 */
@RestController
@RequestMapping("/rest/member")
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/wechat")
    public ResponseData<?> wechatAuth(String code) {
        return ResponseData.OK(memberService.wechatOauth(code));
    }

    @PostMapping("/wechat/info")
    public ResponseData<?> bindInfo(@RequestBody WechatAuth wechatAuth){
        return ResponseData.OK(memberService.bindWechatInfo(wechatAuth));
    }

    @GetMapping("/info")
    public ResponseData<?> getMemberInfo(){
        return ResponseData.OK(memberService.getCurrentMemberInfo());
    }
}
