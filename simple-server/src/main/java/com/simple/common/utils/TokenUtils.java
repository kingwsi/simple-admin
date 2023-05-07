package com.simple.common.utils;

import com.simple.common.bean.AuthUser;
import com.simple.common.entity.member.MemberVO;
import com.simple.common.enumerate.RequestHeader;
import com.simple.common.enumerate.RespCodeEnum;
import com.simple.common.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * description: token处理工具类 <br>
 * date: 2020/9/29 13:18 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
public class TokenUtils {

    public final static String ADMIN_KEY = "admin-test-23984823";
    public final static String MEMBER_KEY = "member-test-0129858347";

    public static String createToken(AuthUser authUser) {
        return Jwts.builder()
                .claim("id", authUser.getId())
                .claim("name", authUser.getUsername())
                .claim("type", "administrator")
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, ADMIN_KEY)
                .compact();
    }

    public static String createMemberToken(AuthUser authUser) {
        return Jwts.builder()
                .claim("id", authUser.getId())
                .claim("name", authUser.getUsername())
                .claim("type", "member")
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, MEMBER_KEY)
                .compact();
    }

    public static AuthUser parser(String token, String key) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Integer id = claims.get("id", Integer.class);
        String name = claims.get("name", String.class);
        AuthUser authUser = new AuthUser();
        authUser.setId(id);
        authUser.setUsername(name);
        return authUser;
    }

    public static AuthUser getCurrentUser() {
        return new AuthUser();
    }

    public static Integer getCurrentPrincipalId() {
        // 获取当前用户
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> requestAttributes.getAttribute(RequestHeader.PRINCIPAL_ID.name(), ServletRequestAttributes.SCOPE_REQUEST))
                .map(obj -> Integer.parseInt(obj.toString()))
                .orElse(0);
    }
}
