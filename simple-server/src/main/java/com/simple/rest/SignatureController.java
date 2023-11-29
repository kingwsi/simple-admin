package com.simple.rest;

import com.simple.common.bean.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * description:  <br>
 * date: 2023/11/29 11:46 <br>
 */
@RestController
@RequestMapping("/rest/signature")
public class SignatureController {
    
    @PostMapping
    public ResponseData<?> signatureTest(@RequestBody Map<Object, Object> requestBody){
        return ResponseData.OK(requestBody);
    }

    @GetMapping
    public ResponseData<?> signatureTestForGET(HttpServletRequest request){
        return ResponseData.OK(request.getParameterMap());
    }
}
