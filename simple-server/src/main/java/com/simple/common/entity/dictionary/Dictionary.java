package com.simple.common.entity.dictionary;

import com.baomidou.mybatisplus.annotation.TableName;
import com.simple.common.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* description: 字典数据 <br>
* date: 2021-06-14 <br>
* author:  <br>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dictionaries")
public class Dictionary extends BaseEntity {

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
