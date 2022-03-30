package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.entity.filterkeyword.FilterKeyword;
import com.simple.common.entity.filterkeyword.FilterKeywordConvertMapper;
import com.simple.common.entity.filterkeyword.FilterKeywordVO;
import com.simple.mapper.FilterKeywordMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
* description: 过滤关键字 Service <br>
* date: 2022-03-30 <br>
* author:  <br>
*/
@Service
public class FilterKeywordService {

    private final FilterKeywordMapper filterkeywordMapper;

    private final FilterKeywordConvertMapper filterkeywordConvertMapper;

    public FilterKeywordService(FilterKeywordMapper filterkeywordMapper, FilterKeywordConvertMapper filterkeywordConvertMapper) {
        this.filterkeywordMapper = filterkeywordMapper;
        this.filterkeywordConvertMapper = filterkeywordConvertMapper;
    }

    /**
     * 插入数据
     *
     * @param filterkeywordVO
     * @return
     */
    public boolean create(FilterKeywordVO filterkeywordVO) {
        return filterkeywordMapper.insert(filterkeywordConvertMapper.toFilterKeyword(filterkeywordVO)) > 0;
    }

    /**
     * 删除一条记录
     *
     * @param id
     * @return
     */
    public boolean removeById(Integer id) {
        return filterkeywordMapper.deleteById(id) > 0;
    }

    /**
     * 获取分页信息
     *
     * @param page
     * @param vo
     * @return
     */
    public IPage<FilterKeywordVO> listOfPage(Page<FilterKeywordVO> page, FilterKeywordVO vo) {
        return filterkeywordMapper.selectPage(page, vo);
    }

    /**
     * 更新数据 id不能为空
     * @param filterkeywordVO
     * @return
     */
    public boolean updateById(FilterKeywordVO filterkeywordVO) {
        Assert.isNull(filterkeywordVO.getId(), "ID不可为空");
        return filterkeywordMapper.updateById(filterkeywordConvertMapper.toFilterKeyword(filterkeywordVO)) > 0;
    }

    public FilterKeywordVO getById(Integer id) {
        FilterKeyword filterkeyword = filterkeywordMapper.selectById(id);
        return filterkeywordConvertMapper.toFilterKeywordVO(filterkeyword);
    }
}
