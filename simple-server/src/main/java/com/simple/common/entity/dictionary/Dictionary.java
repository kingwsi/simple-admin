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

    @TableField("code_value")
    private String value;

    @TableField("code")
    private String code;

    @TableField("description")
    private String description;

    @TableField("group_code")
    private String groupCode;

    @TableField("sort")
    private Integer sort;


}
