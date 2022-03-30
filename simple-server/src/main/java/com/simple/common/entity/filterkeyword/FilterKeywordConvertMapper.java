package com.simple.common.entity.filterkeyword;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* description: 过滤关键字 转换工具 <br>
* date: 2022-03-30 <br>
* author:  <br>
*/
@Component
@Mapper(componentModel = "spring")
public interface FilterKeywordConvertMapper {
    FilterKeyword toFilterKeyword(FilterKeywordVO vo);

    List<FilterKeyword> toFilterKeywordList(List<FilterKeywordVO> vo);

    FilterKeywordVO toFilterKeywordVO(FilterKeyword filterkeyword);

    List<FilterKeywordVO> toFilterKeywordVOList(List<FilterKeyword> vo);
}