package com.simple.common.entity.apiwhitelist;

import com.baomidou.mybatisplus.annotation.TableField;


import com.simple.common.entity.common.BaseEntityVO;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty("路径")
    @TableField("PATH")
    private String path;


}
