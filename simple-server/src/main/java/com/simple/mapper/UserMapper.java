package com.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.entity.user.User;
import com.simple.common.entity.user.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {

    IPage<UserVO> selectUsersOfPage(Page<User> page, @Param("userVO") UserVO userVO);

    /**
     * 获取用户和角色
     * @param userId
     * @return
     */
    UserVO selectUsersWithRoles(@Param("userId") Integer userId);
}
