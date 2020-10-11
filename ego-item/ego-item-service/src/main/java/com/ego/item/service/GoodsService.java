package com.ego.item.service;

import com.ego.commom.PageResult;
import com.ego.item.bo.SpuBo;
import com.ego.item.pojo.Spu;

import java.util.List;

/**
 * @ClassNameGoodsService
 * @Descripiotn //商品业务接口
 * @Author luokun
 * @Date 2020/9/23 16:22
 * @Version 1.0
 **/
public interface GoodsService {
    /**
     * @Author luokun
     * @Description 获取分页结果集Spu
     * @Date  2020/9/23 16:36
     * @Param [key, saleable, page, rows]
     * @return com.ego.commom.PageResult<com.ego.item.pojo.Spu>
     **/
    PageResult<SpuBo> getPaeg(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * @Author luokun
     * @Description 批次获取上架商品数据
     * @Date  2020/9/27 15:27
     * @Param [page, rows]
     * @return java.util.List<com.ego.item.bo.SpuBo>
     **/
    List<SpuBo> getPage(Integer page, Integer rows);

    SpuBo queryGoodsById(Long id);
}
