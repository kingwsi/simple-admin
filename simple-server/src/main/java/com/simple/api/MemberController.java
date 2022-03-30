package com.simple.api;


import com.simple.common.bean.ResponseData;
import com.simple.common.entity.member.MemberVO;
import com.simple.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* description: 会员 controller <br>
* date: 2021-04-13 <br>
* author: ws <br>
*/

@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PutMapping
    public ResponseData<String> updateById(@RequestBody MemberVO memberVO) {
        if (StringUtils.isEmpty(memberVO.getId())) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = memberService.updateById(memberVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @PostMapping
    public ResponseData<String> create(@RequestBody MemberVO memberVO) {
        boolean result = memberService.create(memberVO);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @DeleteMapping("/{id}")
    public ResponseData<String> deleteById(@PathVariable Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseData.FAIL("ID不能为空");
        }
        boolean result = memberService.removeById(id);
        return result ? ResponseData.OK() : ResponseData.FAIL();
    }

    @GetMapping("/page")
    public ResponseData<IPage<MemberVO>> listOfPage(Page<MemberVO> page, MemberVO memberVO) {
        IPage<MemberVO> pageInfo = memberService.listOfPage(page, memberVO);
        return ResponseData.OK(pageInfo);
    }
}
