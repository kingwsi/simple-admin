package com.simple.common.entity.filterkeyword;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.simple.common.entity.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* description: 过滤关键字 <br>
* date: 2022-03-30 <br>
* author:  <br>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("filter_keyword")
public class FilterKeyword extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 类型
     */
    private String type;


}
