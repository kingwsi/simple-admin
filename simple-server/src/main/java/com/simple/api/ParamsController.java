package com.simple.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.simple.common.entity.params.ParamsVO;
import com.simple.service.ParamsService;
import com.simple.common.bean.ResponseData;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* description: 系统参数 controller <br>
* date: 2023-11-11 <br>
* author: ws <br>
*/

@Api(tags = "参数管理")
@RestController
@RequestMapping("/api/params")
public class ParamsController {
    private final ParamsService paramsService;

    public ParamsController(ParamsService paramsService) {
        this.paramsService = paramsService;
    }

    @ApiOperation("更新")
    @PutMapping
    public ResponseData<String> updateById(@RequestBody ParamsVO paramsVO) {
        if (StringUtils.isEmpty(paramsVO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = paramsService.updateById(paramsVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @ApiOperation("创建")
    @PostMapping
    public ResponseData<String> create(@RequestBody ParamsVO paramsVO) {
        boolean result = paramsService.create(paramsVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = paramsService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public ResponseData<IPage<ParamsVO>> listOfPage(Page<ParamsVO> page, ParamsVO paramsVO) {
        IPage<ParamsVO> pageInfo = paramsService.listOfPage(page, paramsVO);
        return ResponseData.OK(pageInfo);
    }
}
