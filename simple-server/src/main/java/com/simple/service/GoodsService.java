package com.simple.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.common.entity.goods.Goods;
import com.simple.common.entity.goods.GoodsConvertMapper;
import com.simple.common.entity.goods.GoodsVO;
import com.simple.mapper.GoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
* description: 房间 Service <br>
* date: 2022-08-13 <br>
* author: ws <br>
*/
@Service
public class GoodsService {

    private final GoodsMapper goodsMapper;

    private final GoodsConvertMapper goodsConvertMapper;

    public GoodsService(GoodsMapper goodsMapper, GoodsConvertMapper goodsConvertMapper) {
        this.goodsMapper = goodsMapper;
        this.goodsConvertMapper = goodsConvertMapper;
    }

    /**
     * 插入数据
     *
     * @param goodsVO
     * @return
     */
    public boolean create(GoodsVO goodsVO) {
        return goodsMapper.insert(goodsConvertMapper.toGoods(goodsVO)) > 0;
    }

    /**
     * 删除一条记录
     *
     * @param id
     * @return
     */
    public boolean removeById(Integer id) {
        return goodsMapper.deleteById(id) > 0;
    }

    /**
     * 获取分页信息
     *
     * @param page
     * @param vo
     * @return
     */
    public IPage<GoodsVO> listOfPage(Page<GoodsVO> page, GoodsVO vo) {
        return goodsMapper.selectPage(page, vo);
    }

    /**
     * 更新数据 id不能为空
     * @param goodsVO
     * @return
     */
    public boolean updateById(GoodsVO goodsVO) {
        return goodsMapper.updateById(goodsConvertMapper.toGoods(goodsVO)) > 0;
    }

    public GoodsVO getById(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        return goodsConvertMapper.toGoodsVO(goods);
    }
}
