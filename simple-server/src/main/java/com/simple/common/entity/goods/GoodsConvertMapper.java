package com.simple.common.entity.goods;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* description: 房间 转换工具 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Component
@Mapper(componentModel = "spring")
public interface GoodsConvertMapper {
    Goods toGoods(GoodsVO vo);

    List<Goods> toGoodsList(List<GoodsVO> vo);

    GoodsVO toGoodsVO(Goods goods);

    List<GoodsVO> toGoodsVOList(List<Goods> vo);
}