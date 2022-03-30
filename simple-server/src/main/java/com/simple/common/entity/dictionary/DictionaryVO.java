package com.simple.common.entity.dictionary;

import com.baomidou.mybatisplus.annotation.TableField;


import com.simple.common.entity.common.BaseEntityVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* description: 字典数据 <br>
* date: 2021-06-14 <br>
* author:  <br>
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryVO extends BaseEntityVO {

    private static final long serialVersionUID = 1L;

    @TableField("VALUE")
    private String value;

    @TableField("CODE")
    private String code;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("GROUP_CODE")
    private String groupCode;

    @TableField("SORT")
    private Integer sort;


}
