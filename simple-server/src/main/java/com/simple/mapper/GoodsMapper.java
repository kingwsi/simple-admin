package com.simple.mapper;

import com.simple.common.entity.goods.Goods;
import com.simple.common.entity.goods.GoodsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
* description: 房间 Mapper接口 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Component
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<GoodsVO> selectPage(Page<GoodsVO> page, @Param("vo") GoodsVO vo);
}
