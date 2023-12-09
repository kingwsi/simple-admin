package com.simple.api;


import com.simple.common.bean.ResponseData;
import com.simple.common.entity.apiwhitelist.ApiWhitelistVO;
import com.simple.service.ApiWhitelistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
* description: api白名单 controller <br>
* date: 2021-06-10 <br>
* author: ws <br>
*/

@Api(tags = "接口白名单")
@RestController
@RequestMapping("/api/api-whitelist")
public class ApiWhitelistController {
    private final ApiWhitelistService apiWhitelistService;

    public ApiWhitelistController(ApiWhitelistService apiWhitelistService) {
        this.apiWhitelistService = apiWhitelistService;
    }

    @ApiOperation("更新")
    @PutMapping
    public ResponseData<String> updateById(@RequestBody ApiWhitelistVO apiWhitelistVO) {
        if (StringUtils.isEmpty(apiWhitelistVO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = apiWhitelistService.updateById(apiWhitelistVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @ApiOperation("创建")
    @PostMapping
    public ResponseData<String> create(@RequestBody ApiWhitelistVO apiWhitelistVO) {
        boolean result = apiWhitelistService.create(apiWhitelistVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = apiWhitelistService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public ResponseData<IPage<ApiWhitelistVO>> listOfPage(Page<ApiWhitelistVO> page, ApiWhitelistVO apiWhitelistVO) {
        IPage<ApiWhitelistVO> pageInfo = apiWhitelistService.listOfPage(page, apiWhitelistVO);
        return ResponseData.OK(pageInfo);
    }

    @ApiOperation("查询全部")
    @GetMapping("/all")
    public ResponseData<List<String>> listAll(String method) {
        return ResponseData.OK(apiWhitelistService.listAllPath(method));
    }
}
