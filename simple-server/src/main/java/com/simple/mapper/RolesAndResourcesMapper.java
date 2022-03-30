package com.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.common.entity.resource.Resource;
import com.simple.common.entity.role.Role;
import com.simple.common.entity.role.RolesAndResources;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: <br>
 * Comments Name: RolesAndResourcesMapper<br>
 * Date: 2019/7/12 9:54<br>
 * Author: ws<br>
 */
@Component
@Mapper
public interface RolesAndResourcesMapper extends BaseMapper<RolesAndResources> {

    @Select("SELECT _r.* FROM sys_roles _r LEFT JOIN sys_roles_and_resources _r_p ON _r.id = _r_p.role_id LEFT JOIN sys_resources _p ON _p.id = _r_p.resource_id WHERE _p.uri = #{resource.uri} AND _p.method = #{resource.method}")
    Role selectRolesByResource(@Param("resource") Resource resource);

    void batchInsertRoleResources(@Param("roleId") Integer roleId, @Param("resourceIds") List<Integer> resourceIds);
}
