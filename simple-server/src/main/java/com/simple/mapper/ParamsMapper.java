package com.simple.mapper;

import com.simple.common.entity.params.Params;
import com.simple.common.entity.params.ParamsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
* description: 系统参数 Mapper接口 <br>
* date: 2023-11-11 <br>
* author: ws <br>
*/
@Component
public interface ParamsMapper extends BaseMapper<Params> {
    IPage<ParamsVO> selectPage(Page<ParamsVO> page, @Param("vo") ParamsVO vo);

    @Select("select `param_value` from sys_params where param_code = #{code}")
    String selectValueByCode(@Param("code") String code);
}
