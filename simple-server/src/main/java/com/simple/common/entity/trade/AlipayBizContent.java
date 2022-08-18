package com.simple.common.entity.trade;

import lombok.Data;

/**
 * description:  <br>
 * date: 2022/8/13 16:58 <br>
 * author: wangshu <br>
 */
@Data
public class AlipayBizContent {
    private String out_trade_no;
    private String total_amount;
    private String subject;
    private String product_code;
}
