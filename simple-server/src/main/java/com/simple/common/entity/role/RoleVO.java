package com.simple.common.entity.role;

import com.simple.common.entity.common.BaseEntityVO;
import com.simple.common.entity.resource.ResourceVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO extends BaseEntityVO {
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("状态 1有效 2无效")
    private String status;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("资源")
    private List<ResourceVO> resourceList;
    @ApiModelProperty("资源id列表")
    private List<Integer> resourceIdList;
}
