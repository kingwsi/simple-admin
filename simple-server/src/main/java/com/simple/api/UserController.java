package com.simple.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.bean.ResponseData;
import com.simple.common.entity.user.User;
import com.simple.common.utils.TokenUtils;
import com.simple.common.entity.user.UserVO;
import com.simple.service.AccessControlService;
import com.simple.service.UserService;

import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户相关接口
 * Name: UserController
 * Author: ws
 * Date: 2019/6/29 23:33
 */
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

    @PostMapping
    public ResponseData<String> createUser(@Validated @RequestBody UserVO user) {
        userService.createUser(user);
        return ResponseData.OK();
    }

    @PutMapping
    public ResponseData<?> update(@Validated(Update.class) @RequestBody UserVO userVO) {
        userService.updateUser(userVO);
        return ResponseData.OK();
    }

    @GetMapping("/page")
    public ResponseData<?> page(Page<User> page, UserVO userVO) {
        return ResponseData.OK(userService.listUsersOfPage(page, userVO));
    }

    @GetMapping("/info")
    public ResponseData<?> getUserInfo() {
        return ResponseData.OK(accessControlService.getUserInfo());
    }

    @DeleteMapping
    public ResponseData<?> deleteUser(HttpServletRequest httpServletRequest) {
        return ResponseData.OK(TokenUtils.getCurrentUser());
    }

    @PostMapping("/password/{id}")
    public ResponseData<?> resetPassword(@PathVariable Integer id) {
        String tmpPassword = userService.resetPasswordById(id);
        return ResponseData.OK(tmpPassword);
    }

    @GetMapping("/apis")
    public ResponseEntity<List> getAllApi() {
        List<HashMap<String, String>> urlList = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            HashMap<String, String> hashMap = new HashMap<>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                hashMap.put("url", url);
            }
            hashMap.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            hashMap.put("methodName", method.getMethod().getName()); // 方法名
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            String type = methodsCondition.toString();
            if (type.startsWith("[") && type.endsWith("]")) {
                type = type.substring(1, type.length() - 1);
                hashMap.put("method", type); // 方法名
            }
            urlList.add(hashMap);
        }
        return ResponseEntity.ok(urlList);
    }

}
