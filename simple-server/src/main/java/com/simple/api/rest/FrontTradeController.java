package com.simple.api.rest;

import com.simple.common.bean.ResponseData;
import com.simple.common.entity.trade.CreateTradeVO;
import com.simple.common.entity.trade.TradeVO;
import com.simple.service.TradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * description:  <br>
 * date: 2022/8/13 16:41 <br>
 * author: wangshu <br>
 */
@RestController
@RequestMapping("/api/trade")
public class FrontTradeController {
    private final TradeService tradeService;

    public FrontTradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }
    
    @PostMapping("/billiards")
    public void createTrade(@RequestBody CreateTradeVO tradeVO) throws IOException {
        tradeService.createBilliardsTrade(tradeVO);
    }

    @GetMapping("/billiards")
    public void testCreateTrade(Integer goodsId) throws IOException {
        CreateTradeVO tradeVO = new CreateTradeVO();
        tradeVO.setGoodsId(goodsId);
        tradeService.createBilliardsTrade(tradeVO);
    }

    @GetMapping("/detail")
    public ResponseData<TradeVO> getTradeDetail(String tradeNo) {
        return ResponseData.OK(tradeService.getByNo(tradeNo));
    }
}
