package com.simple.mapper;

import com.simple.common.entity.filterkeyword.FilterKeyword;
import com.simple.common.entity.filterkeyword.FilterKeywordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
* description: 过滤关键字 Mapper接口 <br>
* date: 2022-03-29 <br>
* author:  <br>
*/
@Component
public interface FilterKeywordMapper extends BaseMapper<FilterKeyword> {
    IPage<FilterKeywordVO> selectPage(Page<FilterKeywordVO> page, @Param("vo") FilterKeywordVO vo);
}
