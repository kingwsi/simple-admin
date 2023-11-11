package com.simple.common.entity.params;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* description: 系统参数 转换工具 <br>
* date: 2023-11-11 <br>
* author: ws <br>
*/
@Component
@Mapper(componentModel = "spring")
public interface ParamsConvertMapper {
    Params toParams(ParamsVO vo);

    List<Params> toParamsList(List<ParamsVO> vo);

    ParamsVO toParamsVO(Params params);

    List<ParamsVO> toParamsVOList(List<Params> vo);
}