package com.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.enumerate.ResourceTypeEnum;
import com.simple.common.entity.resource.Resource;
import com.simple.common.entity.resource.ResourceQuery;
import com.simple.common.entity.resource.ResourceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    @Select("SELECT * FROM sys_resources WHERE type = #{route} AND deleted = 0")
    List<ResourceVO> selectAllByType(@Param("route") ResourceTypeEnum route);

    List<Resource> selectRouteByUserId(@Param("userId") Integer userId);

    List<Resource> selectByUserId(@Param("userId") Integer userId);

    List<Resource> selectByMethodAndUserIdAndUri(@Param("method") String method, @Param("userId") Integer userId);

    List<String> selectUrisByUser(@Param("method") String method, @Param("userId") Integer userId);

    List<Resource> selectByUser(@Param("userId") Integer userId);

    List<Resource> selectByUserAndMethod(@Param("userId") Integer userId, @Param("method") String method);

    IPage<ResourceVO> selectOfPage(Page page, @Param("query") ResourceQuery query);

    int countRepeat(ResourceVO resourceVO);
}
