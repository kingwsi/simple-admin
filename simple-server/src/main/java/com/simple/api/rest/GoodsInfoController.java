package com.simple.api.rest;

import com.simple.common.bean.ResponseData;
import com.simple.service.GoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:  <br>
 * date: 2022/8/13 16:41 <br>
 * author: wangshu <br>
 */
@RestController
@RequestMapping("/api/goods-info")
public class GoodsInfoController {
    
    private final GoodsService goodsService;

    public GoodsInfoController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping("/detail")
    public ResponseData<?> getGoodsInfo(Integer id){
        return ResponseData.OK(goodsService.getById(id));
    }
}
