package com.simple.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.common.entity.goods.GoodsVO;
import com.simple.common.entity.trade.AlipayBizContent;
import com.simple.common.entity.trade.CreateTradeVO;
import com.simple.common.entity.trade.Trade;
import com.simple.common.entity.trade.TradeConvertMapper;
import com.simple.common.entity.trade.TradeVO;
import com.simple.common.entity.tradedetail.TradeDetail;
import com.simple.common.exception.CustomException;
import com.simple.config.AlipayConfig;
import com.simple.mapper.TradeDetailMapper;
import com.simple.mapper.TradeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
* description: 订单 Service <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Slf4j
@Service
@AllArgsConstructor
public class TradeService {

    private final TradeMapper tradeMapper;
    private final TradeDetailMapper tradeDetailMapper;

    private final TradeConvertMapper tradeConvertMapper;
    
    private final GoodsService goodsService;
    
    private final HttpServletRequest httpServletRequest;
    
    private final HttpServletResponse httpServletResponse;
    
    private final ObjectMapper objectMapper;
    
    private final AlipayConfig alipayConfig;
    
    /**
     * 插入数据
     *
     * @param tradeVO
     * @return
     */
    public boolean create(TradeVO tradeVO) {
        return tradeMapper.insert(tradeConvertMapper.toTrade(tradeVO)) > 0;
    }

    /**
     * 删除一条记录
     *
     * @param id
     * @return
     */
    public boolean removeById(Integer id) {
        return tradeMapper.deleteById(id) > 0;
    }

    /**
     * 获取分页信息
     *
     * @param page
     * @param vo
     * @return
     */
    public IPage<TradeVO> listOfPage(Page<TradeVO> page, TradeVO vo) {
        return tradeMapper.selectPage(page, vo);
    }

    /**
     * 更新数据 id不能为空
     * @param tradeVO
     * @return
     */
    public boolean updateById(TradeVO tradeVO) {
        return tradeMapper.updateById(tradeConvertMapper.toTrade(tradeVO)) > 0;
    }

    public TradeVO getById(Integer id) {
        Trade trade = tradeMapper.selectById(id);
        return tradeConvertMapper.toTradeVO(trade);
    }

    public TradeVO getByNo(String tradeNo) {
        LambdaQueryWrapper<Trade> wrapper = new LambdaQueryWrapper<Trade>()
                .eq(Trade::getTradeNo, tradeNo);
        Trade trade = tradeMapper.selectOne(wrapper);
        if (trade == null) {
            return null;
        }
        return tradeConvertMapper.toTradeVO(trade);
    }

    public void createBilliardsTrade(CreateTradeVO tradeVO) throws IOException {
        GoodsVO goods = goodsService.getById(tradeVO.getGoodsId());
        if (goods == null || !"billiards".equals(goods.getType()) || !"0".equals(goods.getStatus())) {
            throw new CustomException("类型错误");
        }
        Trade trade = new Trade();
        LocalDateTime now = LocalDateTime.now();
        String tradeNo = now.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")) + System.nanoTime();
        trade.setTradeNo(tradeNo);
        trade.setName(goods.getGoodsName());
        trade.setTotalPrice(goods.getPrice());
        trade.setStatus("CREATE");
        trade.setCreatedDate(now);
        tradeMapper.insert(trade);
        
        TradeDetail tradeDetail = new TradeDetail();
        tradeDetail.setTradeNo(tradeNo);
        tradeDetail.setGoodsId(goods.getId());
        tradeDetail.setPrice(goods.getPrice());
        tradeDetail.setGoodsName(goods.getGoodsName());
        tradeDetail.setTimeLength(1);
        tradeDetail.setType("billiards");
        tradeDetail.setCreatedDate(now);
        tradeDetailMapper.insert(tradeDetail);

        getPay(tradeConvertMapper.toTradeVO(trade));

//        goods.setStatus("1");
//        goodsService.updateById(goods);
    }
    
    private void getPay(TradeVO tradeVO) throws IOException {
        try{
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getPayGateway(), alipayConfig.getAppid(), alipayConfig.getPrivateKey(), "json", "utf8", alipayConfig.getAlipayPublicKey(), "RSA2"); //获得初始化的AlipayClient
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
            alipayRequest.setReturnUrl(alipayConfig.getReturnUrl() + "?tradeNo=" + tradeVO.getTradeNo());
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());//在公共参数中设置回跳和通知地址
            AlipayBizContent alipayBizContent = new AlipayBizContent();
            alipayBizContent.setOut_trade_no(tradeVO.getTradeNo());
            alipayBizContent.setSubject(tradeVO.getName());
            alipayBizContent.setTotal_amount(tradeVO.getTotalPrice().toString());
            alipayRequest.setBizContent(objectMapper.writeValueAsString(alipayBizContent));//填充业务参数
            String form="";
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().write(form);//直接将完整的表单html输出到页面
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
        } catch (Exception e) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().write("pay fail!");//直接将完整的表单html输出到页面
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
        }
        
    }
}
