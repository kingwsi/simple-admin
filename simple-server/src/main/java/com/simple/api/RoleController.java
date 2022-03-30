package com.simple.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.entity.role.Role;
import com.simple.common.entity.role.RoleVO;
import com.simple.service.RoleService;
import com.simple.common.bean.ResponseData;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseData<?> create(@RequestBody RoleVO roleVO) {
        roleService.createRoleVO(roleVO);
        return ResponseData.OK();
    }

    @GetMapping("/resources")
    public ResponseData<?> listByRoleId(@RequestParam Integer id) {
        RoleVO roleVO = roleService.getRoleWithResources(id);
        return ResponseData.OK(roleVO);
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> delete(@PathVariable("id") Integer id) {
        roleService.deleteById(id);
        return ResponseData.OK();
    }

    @PutMapping
    public ResponseData<?> updateById(@RequestBody RoleVO roleVO) {
        return ResponseData.OK(roleService.updateById(roleVO));
    }

    @PutMapping("/resources")
    public ResponseData<?> updateRoleResources(@RequestBody RoleVO roleVO) {
        roleService.updateRoleResources(roleVO);
        return ResponseData.OK();
    }

    @GetMapping("/page")
    public ResponseData<?> page(Page<Role> page, RoleVO vo) {
        return ResponseData.OK(roleService.listOfPages(page, vo));
    }

    @GetMapping("/list")
    public ResponseData<?> getList(RoleVO vo) {
        return ResponseData.OK(roleService.list(vo));
    }
}
