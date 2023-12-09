package com.simple.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.bean.ResponseData;
import com.simple.common.entity.user.User;
import com.simple.common.entity.user.UserVO;
import com.simple.service.AccessControlService;
import com.simple.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Description: 用户相关接口
 * Name: UserController
 * Author: ws
 * Date: 2019/6/29 23:33
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final AccessControlService accessControlService;

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public UserController(UserService userService, AccessControlService accessControlService, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.userService = userService;
        this.accessControlService = accessControlService;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @ApiOperation("创建")
    @PostMapping
    public ResponseData<String> createUser(@Validated @RequestBody UserVO user) {
        userService.createUser(user);
        return ResponseData.OK();
    }

    @ApiOperation("更新")
    @PutMapping
    public ResponseData<?> update(@Validated(Update.class) @RequestBody UserVO userVO) {
        userService.updateUser(userVO);
        return ResponseData.OK();
    }

    @ApiOperation("获取分页")
    @GetMapping("/page")
    public ResponseData<?> page(Page<User> page, UserVO userVO) {
        return ResponseData.OK(userService.listUsersOfPage(page, userVO));
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public ResponseData<?> getUserInfo() {
        return ResponseData.OK(accessControlService.getUserInfo());
    }


    @ApiOperation("重置密码")
    @PostMapping("/password/{id}")
    public ResponseData<?> resetPassword(@PathVariable Integer id) {
        String tmpPassword = userService.resetPasswordById(id);
        return ResponseData.OK(tmpPassword);
    }

}
