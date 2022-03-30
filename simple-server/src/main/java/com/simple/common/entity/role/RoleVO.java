package com.simple.common.entity.role;

import com.simple.common.entity.common.BaseEntityVO;
import com.simple.common.entity.resource.ResourceVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO extends BaseEntityVO {
    private String name;
    private String status;
    private String description;
    private List<ResourceVO> resourceList;
    private List<Integer> resourceIdList;
}
