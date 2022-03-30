package com.simple.api;


import com.simple.common.bean.ResponseData;
import com.simple.common.entity.apiwhitelist.ApiWhitelistVO;
import com.simple.service.ApiWhitelistService;
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

@RestController
@RequestMapping("/api/api-whitelist")
public class ApiWhitelistController {
    private final ApiWhitelistService apiWhitelistService;

    public ApiWhitelistController(ApiWhitelistService apiWhitelistService) {
        this.apiWhitelistService = apiWhitelistService;
    }

    @PutMapping
    public ResponseData<String> updateById(@RequestBody ApiWhitelistVO apiWhitelistVO) {
        if (StringUtils.isEmpty(apiWhitelistVO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = apiWhitelistService.updateById(apiWhitelistVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @PostMapping
    public ResponseData<String> create(@RequestBody ApiWhitelistVO apiWhitelistVO) {
        boolean result = apiWhitelistService.create(apiWhitelistVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = apiWhitelistService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @GetMapping("/page")
    public ResponseData<IPage<ApiWhitelistVO>> listOfPage(Page<ApiWhitelistVO> page, ApiWhitelistVO apiWhitelistVO) {
        IPage<ApiWhitelistVO> pageInfo = apiWhitelistService.listOfPage(page, apiWhitelistVO);
        return ResponseData.OK(pageInfo);
    }

    @GetMapping("/all")
    public ResponseData<List<String>> listAll(String method) {
        return ResponseData.OK(apiWhitelistService.listAllPath(method));
    }
}
