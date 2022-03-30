package com.simple.common.entity.resource;

import com.simple.common.enumerate.ResourceTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class ResourceVO {
    private Integer id;
    private String name;
    private ResourceTypeEnum type;
    private String description;
    private String uri;
    private String methods;
    private Integer parentId;
    private String sort;
    private String component;
    private String icon;
    private String remark;
    private List<String> resourceIds;

}
