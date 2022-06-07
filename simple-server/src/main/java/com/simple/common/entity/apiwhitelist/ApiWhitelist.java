package com.simple.common.entity.apiwhitelist;

import com.baomidou.mybatisplus.annotation.TableName;
import com.simple.common.entity.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* description: api白名单 <br>
* date: 2021-06-10 <br>
* author: ws <br>
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_api_whitelist")
public class ApiWhitelist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("description")
    private String description;

    @TableField("path")
    private String path;

    @TableField("methods")
    private String methods;

    @TableField("need_key")
    private Boolean needKey;

    @TableField("apikey")
    private String apikey;
}