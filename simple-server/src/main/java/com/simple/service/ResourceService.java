package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.enumerate.ResourceTypeEnum;
import com.simple.common.exception.CustomException;
import com.simple.common.entity.resource.Resource;
import com.simple.common.entity.resource.ResourceConvertMapper;
import com.simple.common.entity.resource.ResourceQuery;
import com.simple.common.entity.resource.ResourceVO;
import com.simple.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceMapper resourceMapper;

    private final ResourceConvertMapper resourceConvertMapper;

    private final AccessControlService accessControlService;

    public ResourceService(ResourceMapper resourceMapper, ResourceConvertMapper resourceConvertMapper, AccessControlService accessControlService) {
        this.resourceMapper = resourceMapper;
        this.resourceConvertMapper = resourceConvertMapper;
        this.accessControlService = accessControlService;
    }

    public void create(ResourceVO vo) {
        if (checkRepeat(vo)) {
            throw new CustomException("资源重复");
        }
        Resource resource = resourceConvertMapper.resourceVOToResource(vo);
        this.resourceMapper.insert(resource);
    }

    public int updateById(ResourceVO vo) {
        if (checkRepeat(vo)) {
            throw new CustomException("资源重复");
        }
        Resource resource = resourceConvertMapper.resourceVOToResource(vo);
        return this.resourceMapper.updateById(resource);
    }

    public List<String> listUrisByMethodAndUser(String method, Integer userId) {
        return resourceMapper.selectUrisByUser(method, userId);
    }

    public List<ResourceVO> listByType(ResourceTypeEnum route) {
        return resourceMapper.selectAllByType(route);
    }

    public IPage<ResourceVO> listOfPage(Page page, ResourceQuery resourceVO) {
        return resourceMapper.selectOfPage(page, resourceVO);
    }

    /**
     * 检查是否重复
     * 规则：uri唯一，MENU类型名称唯一
     *
     * @param resourceVO
     * @return true 重复 false 不重复
     */
    private boolean checkRepeat(ResourceVO resourceVO) {
        int repeatCount = resourceMapper.countRepeat(resourceVO);
        return repeatCount > 0;
    }

    public boolean deleteById(Integer id) {
        int rowCount = resourceMapper.deleteById(id);
        return rowCount > 0;
    }

    public List<ResourceVO> list() {
        List<Resource> resources = resourceMapper.selectList(Wrappers.emptyWrapper());
        return resourceConvertMapper.toResourceVOList(resources);
    }

    public List<ResourceVO> currentUserRouters() {
        List<Resource> resources = resourceMapper.selectRouteByUserId(accessControlService.getUserInfo().getId());
        return resourceConvertMapper.toResourceVOList(resources);
    }
}
