package com.simple.api;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.simple.common.entity.goods.GoodsVO;
import com.simple.service.GoodsService;
import com.simple.common.bean.ResponseData;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* description: 房间 controller <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/

@RestController
@RequestMapping("/api/goods")
public class GoodsController {
    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PutMapping
    public ResponseData<String> updateById(@RequestBody GoodsVO goodsVO) {
        if (StringUtils.isEmpty(goodsVO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = goodsService.updateById(goodsVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @PostMapping
    public ResponseData<String> create(@RequestBody GoodsVO goodsVO) {
        boolean result = goodsService.create(goodsVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = goodsService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @GetMapping("/page")
    public ResponseData<IPage<GoodsVO>> listOfPage(Page<GoodsVO> page, GoodsVO goodsVO) {
        IPage<GoodsVO> pageInfo = goodsService.listOfPage(page, goodsVO);
        return ResponseData.OK(pageInfo);
    }
    
    
}
