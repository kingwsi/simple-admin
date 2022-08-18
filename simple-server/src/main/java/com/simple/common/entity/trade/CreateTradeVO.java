package com.simple.common.entity.trade;

import com.simple.common.entity.common.BaseEntityVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
* description: 订单 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateTradeVO extends BaseEntityVO {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

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
    
    @NotBlank
    private Integer goodsId;
}
