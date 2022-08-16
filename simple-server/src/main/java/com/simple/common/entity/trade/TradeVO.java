package com.simple.common.entity.trade;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.simple.common.entity.common.BaseEntity;
import com.simple.common.entity.common.BaseEntityVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* description: 订单 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class TradeVO extends BaseEntityVO {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private String status;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 外部订单号
     */
    private String outTradeNo;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 总价
     */
    private BigDecimal payTotalPrice;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 时长
     */
    private Integer timeLength;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * openid
     */
    private String openid;
    
}
