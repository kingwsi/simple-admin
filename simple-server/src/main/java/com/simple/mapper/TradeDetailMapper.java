package com.simple.mapper;

import com.simple.common.entity.tradedetail.TradeDetail;
import com.simple.common.entity.tradedetail.TradeDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
* description: 订单明细 Mapper接口 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Component
public interface TradeDetailMapper extends BaseMapper<TradeDetail> {
    IPage<TradeDetailVO> selectPage(Page<TradeDetailVO> page, @Param("vo") TradeDetailVO vo);
}
