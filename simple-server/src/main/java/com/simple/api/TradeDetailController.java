package com.simple.api;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.simple.common.entity.tradedetail.TradeDetailVO;
import com.simple.service.TradeDetailService;
import com.simple.common.bean.ResponseData;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* description: 订单明细 controller <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/

@RestController
@RequestMapping("/api/trade-detail")
public class TradeDetailController {
    private final TradeDetailService tradeDetailService;

    public TradeDetailController(TradeDetailService tradeDetailService) {
        this.tradeDetailService = tradeDetailService;
    }

    @PutMapping
    public ResponseData<String> updateById(@RequestBody TradeDetailVO tradeDetailVO) {
        if (StringUtils.isEmpty(tradeDetailVO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = tradeDetailService.updateById(tradeDetailVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @PostMapping
    public ResponseData<String> create(@RequestBody TradeDetailVO tradeDetailVO) {
        boolean result = tradeDetailService.create(tradeDetailVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = tradeDetailService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @GetMapping("/page")
    public ResponseData<IPage<TradeDetailVO>> listOfPage(Page<TradeDetailVO> page, TradeDetailVO tradeDetailVO) {
        IPage<TradeDetailVO> pageInfo = tradeDetailService.listOfPage(page, tradeDetailVO);
        return ResponseData.OK(pageInfo);
    }
}
