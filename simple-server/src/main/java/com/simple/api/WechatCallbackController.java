package com.simple.api;

import com.simple.service.WechatCallbackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description:  <br>
 * date: 2023/5/7 15:40 <br>
 */
@RestController
@RequestMapping("/api/wechat-callback")
public class WechatCallbackController {
    
    private final WechatCallbackService wechatCallbackService;

    public WechatCallbackController(WechatCallbackService wechatCallbackService) {
        this.wechatCallbackService = wechatCallbackService;
    }

    @GetMapping(value = "/checkSign")
    public String checkSign(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 接收微信服务器以Get请求发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        return wechatCallbackService.checkSignature(signature, timestamp, nonce, echostr);
    }
}
