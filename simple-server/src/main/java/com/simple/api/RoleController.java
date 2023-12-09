package com.simple.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.entity.role.Role;
import com.simple.common.entity.role.RoleVO;
import com.simple.service.RoleService;
import com.simple.common.bean.ResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("创建")
    @PostMapping
    public ResponseData<?> create(@RequestBody RoleVO roleVO) {
        roleService.createRoleVO(roleVO);
        return ResponseData.OK();
    }

    @ApiOperation("获取角色资源")
    @GetMapping("/resources")
    public ResponseData<?> resources(@RequestParam Integer id) {
        RoleVO roleVO = roleService.getRoleWithResources(id);
        return ResponseData.OK(roleVO);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public ResponseData<?> delete(@PathVariable("id") Integer id) {
        roleService.deleteById(id);
        return ResponseData.OK();
    }

    @ApiOperation("更新")
    @PutMapping
    public ResponseData<?> updateById(@RequestBody RoleVO roleVO) {
        return ResponseData.OK(roleService.updateById(roleVO));
    }

    @ApiOperation("更新角色资源")
    @PutMapping("/resources")
    public ResponseData<?> updateRoleResources(@RequestBody RoleVO roleVO) {
        roleService.updateRoleResources(roleVO);
        return ResponseData.OK();
    }

    @ApiOperation("获取分页")
    @GetMapping("/page")
    public ResponseData<?> page(Page<Role> page, RoleVO vo) {
        return ResponseData.OK(roleService.listOfPages(page, vo));
    }

    @ApiOperation("获取角色列表")
    @GetMapping("/list")
    public ResponseData<?> getList(RoleVO vo) {
        return ResponseData.OK(roleService.list(vo));
    }
}
