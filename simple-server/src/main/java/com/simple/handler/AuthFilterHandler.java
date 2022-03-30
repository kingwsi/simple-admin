package com.simple.handler;

import com.simple.common.bean.AuthUser;
import com.simple.common.bean.ResponseData;
import com.simple.common.enumerate.RequestHeader;
import com.simple.common.utils.AntPathMatcherExt;
import com.simple.common.utils.TokenUtils;
import com.simple.service.ResourceService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

/**
 * description:  <br>
 * date: 2022/3/29 14:05 <br>
 * author: ws <br>
 */
@Slf4j
public class AuthFilterHandler implements Filter {

    final ResourceService resourceService;

    private static final String[] excludedAuthPages = {
            "/api/debug/**",
            "/api/filter-keyword/*",
            "/api/auth",
            "/api/verification/captcha",
            "/v3/api-docs",
    };

    private final AntPathMatcherExt antPathMatcherExt = new AntPathMatcherExt();

    public AuthFilterHandler(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String path = request.getServletPath();
        // 白名单校验
        if (!antPathMatcherExt.pathMatch(excludedAuthPages, path)) {
            if (!StringUtils.isEmpty(token)) {
                try {
                    AuthUser authUser = TokenUtils.parser(token.replace("Bearer ", ""));
                    String method = Objects.requireNonNull(request.getMethod());
                    // 权限验证
                    List<String> apis = resourceService.listUrisByMethodAndUser(method, authUser.getId());
                    if (antPathMatcherExt.pathMatch(apis, path)) {
                        request.setAttribute(RequestHeader.PRINCIPAL_ID.name(), authUser.getId());
                        filterChain.doFilter(request, servletResponse);
                    }
                } catch (Exception e) {
                    log.warn("权限验证失败->{}", e.getMessage());
                    responseFail((HttpServletResponse) servletResponse, "Forbidden");
                }
            } else {
                responseFail((HttpServletResponse) servletResponse, "Forbidden");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    protected void responseFail(HttpServletResponse response, String message) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        JsonMapper jsonMapper = new JsonMapper();
        out.print(jsonMapper.writeValueAsString(ResponseData.OK(message)));
        out.flush();
    }
}
