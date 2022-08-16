package com.simple.common.entity.tradedetail;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.simple.common.entity.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* description: 订单明细 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("trade_detail")
public class TradeDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 总价
     */
    private BigDecimal price;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 订单号
     */
    private BigDecimal payTotalPrice;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    private String goodsName;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 类型
     */
    private String type;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 时长
     */
    private Integer timeLength;


}
