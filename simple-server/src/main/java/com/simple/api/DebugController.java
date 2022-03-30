package com.simple.api;

import com.simple.common.bean.AuthUser;
import com.simple.common.utils.TokenUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: <br>
 * Comments Name: DebugController<br>
 * Date: 2019/8/19 15:37<br>
 * Author: ws<br>
 */
@RestController
@RequestMapping("/api/debug")
@Slf4j
public class DebugController {

    @GetMapping("/header")
    public String sessionTest(HttpServletRequest request) {
//        roleService.listRoles();
        String name = request.getHeader("h-name");
        String type = request.getHeader("h-type");
        String id = request.getHeader("h-id");
        log.info("name->{},type->{},id->{}", name, type, id);
        return "SUCCESS";
    }

    @GetMapping("/token")
    public String generatorToken(String username) {
        if (StringUtils.isEmpty(username)) {
            username = "admin";
        }
        AuthUser authUser = new AuthUser();
        authUser.setUsername(username);
        authUser.setId(1);
        return TokenUtils.createToken(authUser);
    }
}
