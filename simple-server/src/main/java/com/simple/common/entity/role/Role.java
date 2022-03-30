package com.simple.common.entity.role;

import com.baomidou.mybatisplus.annotation.TableName;
import com.simple.common.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 角色<br>
 * Comments Name: Role<br>
 * Date: 2019/7/11 16:29<br>
 * Author: ws<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_roles")
public class Role extends BaseEntity {
    private String name;
    private String status;
    private String description;
}
