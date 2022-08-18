package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.entity.tradedetail.TradeDetail;
import com.simple.common.entity.tradedetail.TradeDetailConvertMapper;
import com.simple.common.entity.tradedetail.TradeDetailVO;
import com.simple.mapper.TradeDetailMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
* description: 订单明细 Service <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Service
public class TradeDetailService {

    private final TradeDetailMapper tradedetailMapper;

    private final TradeDetailConvertMapper tradedetailConvertMapper;

    public TradeDetailService(TradeDetailMapper tradedetailMapper, TradeDetailConvertMapper tradedetailConvertMapper) {
        this.tradedetailMapper = tradedetailMapper;
        this.tradedetailConvertMapper = tradedetailConvertMapper;
    }

    /**
     * 插入数据
     *
     * @param tradedetailVO
     * @return
     */
    public boolean create(TradeDetailVO tradedetailVO) {
        return tradedetailMapper.insert(tradedetailConvertMapper.toTradeDetail(tradedetailVO)) > 0;
    }

    /**
     * 删除一条记录
     *
     * @param id
     * @return
     */
    public boolean removeById(Integer id) {
        return tradedetailMapper.deleteById(id) > 0;
    }

    /**
     * 获取分页信息
     *
     * @param page
     * @param vo
     * @return
     */
    public IPage<TradeDetailVO> listOfPage(Page<TradeDetailVO> page, TradeDetailVO vo) {
        return tradedetailMapper.selectPage(page, vo);
    }

    /**
     * 更新数据 id不能为空
     * @param tradedetailVO
     * @return
     */
    public boolean updateById(TradeDetailVO tradedetailVO) {
        return tradedetailMapper.updateById(tradedetailConvertMapper.toTradeDetail(tradedetailVO)) > 0;
    }

    public TradeDetailVO getById(Integer id) {
        TradeDetail tradedetail = tradedetailMapper.selectById(id);
        return tradedetailConvertMapper.toTradeDetailVO(tradedetail);
    }
}
