package com.simple.common.entity.apiwhitelist;

import com.baomidou.mybatisplus.annotation.TableField;


import com.simple.common.entity.common.BaseEntityVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* description: api白名单 <br>
* date: 2021-06-10 <br>
* author: ws <br>
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiWhitelistVO extends BaseEntityVO {

    private static final long serialVersionUID = 1L;

    @TableField("DESCRIPTION")
    private String description;

    @TableField("PATH")
    private String path;


}
