package com.simple.api;

import com.simple.common.bean.ResponseData;
import com.simple.service.OssService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * description: OssController <br>
 * date: 2020/8/13 15:27 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Api(tags = "OSS")
@RestController
@RequestMapping("/api/oss")
public class OssController {

    private final OssService ossService;

    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @ApiOperation("上传图片")
    @PostMapping("/image")
    public ResponseData<String> uploadFile(@RequestParam("file") MultipartFile file){
//        String path = ossService.uploadImage(file);
        String path = "https://vant-admin.oss-cn-hangzhou.aliyuncs.com/c749e499-ef95-49b1-9336-c2d1c2267552.png";
        if (StringUtils.isEmpty(path)) {
            return ResponseData.FAIL();
        }
        return ResponseData.OK(path);
    }
}
