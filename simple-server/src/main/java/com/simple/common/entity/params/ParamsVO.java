package com.simple.common.entity.params;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.simple.common.entity.common.BaseEntity;
import com.simple.common.entity.common.BaseEntityVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
* description: 系统参数 <br>
* date: 2023-11-11 <br>
* author: ws <br>
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class ParamsVO extends BaseEntityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 值
     */
    private String paramValue;

    /**
     * 编码
     */
    private String paramCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 分组CODE
     */
    private String groupCode;

    /**
     * 排序
     */
    private Integer sort;


}
