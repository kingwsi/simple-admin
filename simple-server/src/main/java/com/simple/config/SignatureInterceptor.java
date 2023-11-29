package com.simple.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.simple.common.bean.AuthUser;
import com.simple.common.bean.ResponseData;
import com.simple.common.enumerate.RequestHeader;
import com.simple.common.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * description:  <br>
 * date: 2023/11/12 11:41 <br>
 */
@Slf4j
public class SignatureInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    private final HashSet<String> excludePath;

    public SignatureInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.excludePath = new HashSet<>();
        this.excludePath.add("/rest/member/wechat/info");
        this.excludePath.add("/rest/member/wechat");
        this.excludePath.add("/rest/signature");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在这里进行签名验证
        boolean isSignatureValid = verifySignature(request);
        if (!isSignatureValid) {
            // 验证失败，可以返回错误信息或者重定向到错误页面
            responseFail(response, "Failed to verify signature");
            return false;
        }
        // 白名单校验
        if (excludePath.contains(request.getServletPath())) {
            return true;
        } else {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!StringUtils.isEmpty(token)) {
                try {
                    AuthUser authUser = TokenUtils.parser(token.replace("Bearer ", ""), TokenUtils.MEMBER_KEY);
                    request.setAttribute(RequestHeader.PRINCIPAL_ID.name(), authUser.getId());
                    return true;
                } catch (Exception e) {
                    log.warn("权限验证失败->{}", e.getMessage());
                    responseFail(response, "FORBIDDEN");
                }
            } else {
                responseFail(response, "FORBIDDEN");
            }
        }
        return false;
    }

    /**
     * return Fail Response Msg
     */
    protected void responseFail(ServletResponse servletResponse, String message) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        JsonMapper jsonMapper = new JsonMapper();
        servletResponse.getWriter().write(jsonMapper.writeValueAsString(ResponseData.FAIL(message, HttpServletResponse.SC_FORBIDDEN)));
        servletResponse.getWriter().flush();
        servletResponse.getWriter().close();
    }

    private boolean verifySignature(HttpServletRequest request) {
        try {
            String signature = request.getHeader("sign");
            String timestamp = request.getHeader("timestamp");
            if (StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(signature)) {
                return false;
            }
            // 1分钟
            long df = System.currentTimeMillis() - Long.parseLong(timestamp);
            if (df > 60 * 1000 || df < 0) {
                return false;
            }
            String paramsStr;
            if (HttpMethod.POST.name().equals(request.getMethod()) || HttpMethod.PUT.name().equals(request.getMethod())) {
                // 上传文件暂不验证签名
                if (MediaType.MULTIPART_FORM_DATA_VALUE.equals(request.getContentType())) {
                    return true;
                }
                // 获取 body 参数
                Map<String, Object> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
                // 对Map对象进行排序
                Map<String, Object> sortedMap = sortMap(requestMap);
                // 将排序后的Map转换为字符串
                paramsStr = objectMapper.writeValueAsString(sortedMap);
            } else {
                Map<String, String[]> parameterMap = request.getParameterMap();
                String[] keys = parameterMap.keySet().toArray(new String[0]);
                Arrays.sort(keys);
                StringBuilder sb = new StringBuilder();
                for (String key : keys) {
                    String value = Arrays.stream(parameterMap.get(key)).findAny().orElse("");
                    sb.append(key).append(value);
                }
                paramsStr = sb.toString();
            }
            // 计算签名
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            StringBuilder builder = new StringBuilder();
            for (byte b : md.digest((paramsStr + timestamp).getBytes())) {
                builder.append(String.format("%02x", b));
            }
            String signatureToVerify = builder.toString();
            // 比较签名
            return signatureToVerify.equals(signature);
        } catch (Exception e) {
            log.error("签名验证异常！{}", e.getMessage());
        }
        return false;
    }

    private Map<String, Object> sortMap(Map<String, Object> map) {
        Map<String, Object> sortedMap = new TreeMap<String, Object>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                // 如果Map中的Value也是一个Map类型，则递归进行排序
                sortedMap.put(entry.getKey(), sortMap((Map<String, Object>) entry.getValue()));
            } else {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
        }
        return sortedMap;
    }
}