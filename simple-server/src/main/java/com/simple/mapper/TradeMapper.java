package com.simple.mapper;

import com.simple.common.entity.trade.Trade;
import com.simple.common.entity.trade.TradeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
* description: 订单 Mapper接口 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Component
public interface TradeMapper extends BaseMapper<Trade> {
    IPage<TradeVO> selectPage(Page<TradeVO> page, @Param("vo") TradeVO vo);
}
