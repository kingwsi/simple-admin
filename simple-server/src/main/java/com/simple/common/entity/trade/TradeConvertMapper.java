package com.simple.common.entity.trade;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* description: 订单 转换工具 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Component
@Mapper(componentModel = "spring")
public interface TradeConvertMapper {
    Trade toTrade(TradeVO vo);

    List<Trade> toTradeList(List<TradeVO> vo);

    TradeVO toTradeVO(Trade trade);

    List<TradeVO> toTradeVOList(List<Trade> vo);
}