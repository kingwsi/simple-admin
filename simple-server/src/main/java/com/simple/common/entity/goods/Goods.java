package com.simple.common.entity.goods;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.simple.common.entity.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* description: 房间 <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("goods")
public class Goods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 状态描述
     */
    private String status;

    private String description;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 类型
     */
    private String type;

    /**
     * 商品封面
     */
    private String cover;

    /**
     * 时长
     */
    private Integer timeLength;


}
