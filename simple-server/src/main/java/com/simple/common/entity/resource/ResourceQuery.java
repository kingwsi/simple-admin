package com.simple.common.entity.resource;

import com.simple.common.enumerate.ResourceTypeEnum;
import lombok.Data;

@Data
public class ResourceQuery {
    private String name;
    private ResourceTypeEnum type;
}
