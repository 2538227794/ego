package com.ego.item.service;

import com.ego.commom.PageResult;
import com.ego.item.pojo.Brand;

import java.util.List;

/**
 * @ClassNameBrandService
 * @Descripiotn 品牌业务接口
 * @Author luokun
 * @Date 2020/9/21 10:58
 * @Version 1.0
 **/
public interface BrandService {

    /**
     *
     * @Author luokun
     * @Description 品牌分页
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    PageResult<Brand> getPage(Integer pageNo, Integer pageSize, String sortBy, Boolean descending, String key);

    /**
     *
     * @Author luokun
     * @Description 新增品牌
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    void save(Brand brand, Long[] cids);

   /**
    *
    * @Author luokun
    * @Description 修改品牌
    * @Date 2020/7/8 10:05
    * @param
    * @return
    **/
    void update(Brand brand, Long[] cids);

    /**
     *
     * @Author luokun
     * @Description 删除品牌
     * @Date 2020/7/8 10:05
     * @param id 品牌id
     * @return
     **/
    void deleteById(Long id);

    /**
     * @Author luokun
     * @Description 获取品牌根据商品类id
     * @Date  2020/9/23 21:49
     * @Param []
     * @return java.util.List<com.ego.item.pojo.Brand>
     **/
    List<Brand> getBrandListByCategoryId(Long categoryId);

    List<Brand> findListByIds(List<Long> idList);
}
