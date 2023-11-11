package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.entity.params.Params;
import com.simple.common.entity.params.ParamsConvertMapper;
import com.simple.common.entity.params.ParamsVO;
import com.simple.common.exception.CustomException;
import com.simple.mapper.ParamsMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
* description: 系统参数 Service <br>
* date: 2023-11-11 <br>
* author: ws <br>
*/
@Service
public class ParamsService {

    private final ParamsMapper paramsMapper;

    private final ParamsConvertMapper paramsConvertMapper;

    public ParamsService(ParamsMapper paramsMapper, ParamsConvertMapper paramsConvertMapper) {
        this.paramsMapper = paramsMapper;
        this.paramsConvertMapper = paramsConvertMapper;
    }

    /**
     * 插入数据
     *
     * @param paramsVO
     * @return
     */
    public boolean create(ParamsVO paramsVO) {
        return paramsMapper.insert(paramsConvertMapper.toParams(paramsVO)) > 0;
    }

    /**
     * 删除一条记录
     *
     * @param id
     * @return
     */
    public boolean removeById(Integer id) {
        return paramsMapper.deleteById(id) > 0;
    }

    /**
     * 获取分页信息
     *
     * @param page
     * @param vo
     * @return
     */
    public IPage<ParamsVO> listOfPage(Page<ParamsVO> page, ParamsVO vo) {
        return paramsMapper.selectPage(page, vo);
    }

    /**
     * 更新数据 id不能为空
     * @param paramsVO
     * @return
     */
    public boolean updateById(ParamsVO paramsVO) {
        return paramsMapper.updateById(paramsConvertMapper.toParams(paramsVO)) > 0;
    }

    public ParamsVO getById(Integer id) {
        Params params = paramsMapper.selectById(id);
        return paramsConvertMapper.toParamsVO(params);
    }

    public String getValueByCode(String code) {
        String value = paramsMapper.selectValueByCode(code);
        if (value == null) {
            throw new CustomException("未配置参数：" + code);
        }
        return value;
    }
}
