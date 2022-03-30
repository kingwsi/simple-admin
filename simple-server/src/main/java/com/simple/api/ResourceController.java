package com.simple.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.bean.ResponseData;
import com.simple.common.entity.resource.ResourceQuery;
import com.simple.common.entity.resource.ResourceVO;
import com.simple.service.ResourceService;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 路由
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/list")
    public ResponseData<?> list(HttpServletRequest request) {
        return ResponseData.OK(resourceService.list());
    }

    @GetMapping("/routes")
    public ResponseData<?> listRoutes() {
        return ResponseData.OK(resourceService.currentUserRouters());
    }

    @PostMapping
    public ResponseData<?> create(@RequestBody ResourceVO resourceVO) {
        resourceService.create(resourceVO);
        return ResponseData.OK();
    }

    @PutMapping
    public ResponseData<?> updateById(@RequestBody ResourceVO resourceVO) {
        if (StringUtils.isEmpty(resourceVO.getId())){
            return ResponseData.FAIL("id不能为空");
        }
        resourceService.updateById(resourceVO);
        return ResponseData.OK();
    }

    @GetMapping("/page")
    public ResponseData<?> page(Page page, ResourceQuery resourceVO) {
        return ResponseData.OK(resourceService.listOfPage(page, resourceVO));
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteById(@PathVariable Integer id) {
        return resourceService.deleteById(id) ? ResponseData.OK() : ResponseData.FAIL("删除失败");
    }

    /**
     * 获取用户所有接口
     * @param method 请求方式
     * @param userId 用户标识
     * @return
     */
    @GetMapping("/apis")
    public ResponseData<?> listCurrentUserApis(String method, Integer userId) {
        List<String> list = resourceService.listUrisByMethodAndUser(method, userId);
        System.out.println("apis request");
        return ResponseData.OK(list);
    }
}
