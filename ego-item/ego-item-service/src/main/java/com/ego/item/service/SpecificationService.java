package com.ego.item.service;

import com.ego.item.pojo.Specification;

/**
 * @ClassName SpecificationService
 * @Descripiotn 商品类规格业务接口
 * @Author luokun
 * @Date 2020/9/23 14:16
 * @Version 1.0
 **/
public interface SpecificationService {
    /**
     * @Author luokun
     * @Description 根据商品类id查询商品类规格json
     * @Date  2020/9/23 14:25
     * @Param [categoryId] 商品类id
     * @return java.lang.String
     **/
    String getSpecifications(Long categoryId);

    /**
     * @Author luokun
     * @Description 新增商品类规格
     * @Date  2020/9/23 14:36
     * @Param [specification]
     * @return void
     **/
    void add(Specification specification);

    /**
     * @Author luokun
     * @Description 修改商品类规格
     * @Date  2020/9/23 15:58
     * @Param [specification]
     * @return void
     **/
    void update(Specification specification);
}
