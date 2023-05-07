package com.simple.handler;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.simple.common.bean.AuthUser;
import com.simple.common.bean.ResponseData;
import com.simple.common.enumerate.RequestHeader;
import com.simple.common.utils.TokenUtils;
import com.simple.service.ApiWhitelistService;
import com.simple.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * description:  <br>
 * date: 2022/3/29 14:05 <br>
 * author: ws <br>
 */
@Slf4j
public class RestAuthFilterHandler extends AntPathMatcher implements Filter {

    final ResourceService resourceService;
    
    final ApiWhitelistService apiWhitelistService;

    final HashSet<String> excludePath;
    
    

    public RestAuthFilterHandler(ResourceService resourceService, ApiWhitelistService apiWhitelistService) {
        this.resourceService = resourceService;
        this.apiWhitelistService = apiWhitelistService;
        this.excludePath = new HashSet<>();
        this.excludePath.add("/rest/member/wechat/info");
        this.excludePath.add("/rest/member/wechat");
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
        // TODO 签名
        // 白名单校验
        if (excludePath.contains(request.getServletPath())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (!StringUtils.isEmpty(token)) {
                try {
                    AuthUser authUser = TokenUtils.parser(token.replace("Bearer ", ""), TokenUtils.MEMBER_KEY);
                    request.setAttribute(RequestHeader.PRINCIPAL_ID.name(), authUser.getId());
                    filterChain.doFilter(request, servletResponse);
                } catch (Exception e) {
                    log.warn("权限验证失败->{}", e.getMessage());
                    responseFail((HttpServletResponse) servletResponse, "FORBIDDEN");
                }
            } else {
                responseFail((HttpServletResponse) servletResponse, "FORBIDDEN");
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
        // TODO 白名单检查
        return true;
    }
}
