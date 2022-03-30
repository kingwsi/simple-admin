package com.simple.common.entity.filterkeyword;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.simple.common.entity.common.BaseEntity;
import com.simple.common.entity.common.BaseEntityVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* description: 过滤关键字 <br>
* date: 2022-03-30 <br>
* author:  <br>
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class FilterKeywordVO extends BaseEntityVO {

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
