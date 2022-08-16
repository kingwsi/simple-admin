package com.simple.api;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.simple.common.entity.trade.TradeVO;
import com.simple.service.TradeService;
import com.simple.common.bean.ResponseData;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* description: 订单 controller <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PutMapping
    public ResponseData<String> updateById(@RequestBody TradeVO tradeVO) {
        if (StringUtils.isEmpty(tradeVO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = tradeService.updateById(tradeVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @PostMapping
    public ResponseData<String> create(@RequestBody TradeVO tradeVO) {
        boolean result = tradeService.create(tradeVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = tradeService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @GetMapping("/page")
    public ResponseData<IPage<TradeVO>> listOfPage(Page<TradeVO> page, TradeVO tradeVO) {
        IPage<TradeVO> pageInfo = tradeService.listOfPage(page, tradeVO);
        return ResponseData.OK(pageInfo);
    }
}
