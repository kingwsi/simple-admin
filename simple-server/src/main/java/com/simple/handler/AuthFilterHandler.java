package com.simple.handler;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.simple.common.bean.AuthUser;
import com.simple.common.bean.ResponseData;
import com.simple.common.entity.apiwhitelist.ApiWhitelist;
import com.simple.common.enumerate.RequestHeader;
import com.simple.common.utils.TokenUtils;
import com.simple.service.ApiWhitelistService;
import com.simple.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * description:  <br>
 * date: 2022/3/29 14:05 <br>
 * author: ws <br>
 */
@Slf4j
public class AuthFilterHandler extends AntPathMatcher implements Filter {

    final ResourceService resourceService;
    
    final ApiWhitelistService apiWhitelistService;

    public AuthFilterHandler(ResourceService resourceService, ApiWhitelistService apiWhitelistService) {
        this.resourceService = resourceService;
        this.apiWhitelistService = apiWhitelistService;
    }

    /**
     * request filter and Authorization
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 白名单校验
        if (checkApiWhitelist(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (!StringUtils.isEmpty(token)) {
                try {
                    AuthUser authUser = TokenUtils.parser(token.replace("Bearer ", ""));
                    // 权限验证
                    List<String> apis = resourceService.listUrisByMethodAndUser(request.getMethod(), authUser.getId());
                    if (pathMatch(apis, request.getServletPath())) {
                        request.setAttribute(RequestHeader.PRINCIPAL_ID.name(), authUser.getId());
                        filterChain.doFilter(request, servletResponse);
                    } else {
                        throw new RuntimeException("没有权限");
                    }
                } catch (Exception e) {
                    log.warn("权限验证失败->{}", e.getMessage());
                    responseFail((HttpServletResponse) servletResponse, "无效的token");
                }
            } else {
                responseFail((HttpServletResponse) servletResponse, "需要token");
            }
        }
    }

    /**
     * return Fail Response Msg
     * @param response
     * @param message
     * @throws IOException
     */
    protected void responseFail(HttpServletResponse response, String message) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        JsonMapper jsonMapper = new JsonMapper();
        out.print(jsonMapper.writeValueAsString(ResponseData.FAIL(message)));
        out.flush();
    }

    /**
     * check whitelist
     * @param request
     * @return
     */
    public boolean checkApiWhitelist(HttpServletRequest request) {
        List<ApiWhitelist> whitelists = apiWhitelistService.listAll(request.getMethod());
        String path = request.getServletPath();
        String apikey = request.getParameter("apikey");
        for (ApiWhitelist whitelist : whitelists) {
            if (super.match(whitelist.getPath(), path)) {
                if (whitelist.getNeedKey()) {
                    return whitelist.getApikey().equals(apikey);
                }
                return true;
            }
        }
        return false;
    }

    public boolean pathMatch(String[] paths, String pattern) {
        for (String path : paths) {
            if (super.match(path, pattern)) {
                return true;
            }
        }
        return false;
    }

    public boolean pathMatch(List<String> paths, String pattern) {
        if (paths == null || paths.isEmpty()) {
            return false;
        }
        for (String path : paths) {
            if (super.match(path, pattern)) {
                return true;
            }
        }
        return false;
    }
}
