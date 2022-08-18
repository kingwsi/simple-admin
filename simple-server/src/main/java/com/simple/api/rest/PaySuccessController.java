package com.simple.api.rest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.simple.common.entity.trade.TradeVO;
import com.simple.config.AlipayConfig;
import com.simple.service.TradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * description:  <br>
 * date: 2022/8/13 20:58 <br>
 * author: wangshu <br>
 */
@RestController
@RequestMapping("/api/pay-success")
public class PaySuccessController {
    
    private final AlipayConfig alipayConfig;
    
    private final TradeService tradeService;

    public PaySuccessController(AlipayConfig alipayConfig, TradeService tradeService) {
        this.alipayConfig = alipayConfig;
        this.tradeService = tradeService;
    }

    @GetMapping("/alipay")
    public void alipayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        HashMap<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((k,v)->{
            if (k!=null){
                params.put(k, v[0]);
            }
        });
        AlipaySignature.verifyV1(params, alipayConfig.getPublicKey(), params.get("charset"), params.get("sign_type"));
        String tradeNo = params.get("out_trade_no");
        BigDecimal totalAmount = new BigDecimal(params.get("total_amount"));
        String outTradeNo = params.get("trade_no");
        TradeVO tradeVO = tradeService.getByNo(tradeNo);
        if (tradeVO != null && tradeVO.getTotalPrice().compareTo(totalAmount) == 0) {
            tradeVO.setOutTradeNo(outTradeNo);
            tradeVO.setPayTime(LocalDateTime.now());
            tradeVO.setPayTotalPrice(totalAmount);
            tradeVO.setStatus("PAY");
            tradeService.updateById(tradeVO);
            out.print("success");
        } else {
            out.print("fail");
        }
        out.flush();
        out.close();
    }

    @GetMapping("/sync-alipay")
    public void syncAlipayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        HashMap<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((k,v)->{
            if (k!=null){
                params.put(k, v[0]);
            }
        });
        AlipaySignature.verifyV1(params, alipayConfig.getPublicKey(), params.get("charset"), params.get("sign_type"));
        String tradeNo = params.get("out_trade_no");
        BigDecimal totalAmount = new BigDecimal(params.get("total_amount"));
        String outTradeNo = params.get("trade_no");
        TradeVO tradeVO = tradeService.getByNo(tradeNo);
        if (tradeVO != null && tradeVO.getTotalPrice().compareTo(totalAmount) == 0) {
            tradeVO.setOutTradeNo(outTradeNo);
            tradeVO.setPayTime(LocalDateTime.now());
            tradeVO.setPayTotalPrice(totalAmount);
            tradeVO.setStatus("PAY");
            tradeService.updateById(tradeVO);
            response.setHeader("Location",alipayConfig.getPayResultpage() + "?tradeNo=" + tradeNo);
            response.setStatus(302);
            response.sendRedirect(alipayConfig.getPayResultpage() + "?tradeNo=" + tradeNo);
        } else {
            out.print("fail");
        }
    }
}
