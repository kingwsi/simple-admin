package com.simple.api;


import com.simple.common.bean.ResponseData;
import com.simple.common.entity.dictionary.DictionaryVO;
import com.simple.service.DictionaryService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* description: 字典数据 controller <br>
* date: 2021-06-14 <br>
* author:  <br>
*/

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PutMapping
    public ResponseData<String> updateById(@RequestBody DictionaryVO dictionaryVO) {
        if (StringUtils.isEmpty(dictionaryVO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = dictionaryService.updateById(dictionaryVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @PostMapping
    public ResponseData<String> create(@RequestBody DictionaryVO dictionaryVO) {
        boolean result = dictionaryService.create(dictionaryVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = dictionaryService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @GetMapping("/page")
    public ResponseData<IPage<DictionaryVO>> listOfPage(Page<DictionaryVO> page, DictionaryVO dictionaryVO) {
        IPage<DictionaryVO> pageInfo = dictionaryService.listOfPage(page, dictionaryVO);
        return ResponseData.OK(pageInfo);
    }
}
