package com.ego.item.service;

import com.ego.item.pojo.Category;

import java.util.List;

/**
 * @ClassNameCategoryService
 * @Descripiotn 商品类业务接口
 * @Author luokun
 * @Date 2020/9/21 10:32
 * @Version 1.0
 **/
public interface CategoryService {
    /**
     *
     * @Author luokun
     * @Description 根据pid查询商品类
     * @Date 2020/7/8 10:05
     * @param pid 父id
     * @return
     **/
    List<Category> get(Long pid);

    /**
     *
     * @Author luokun
     * @Description 新增商品类
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    void add(Category category);

    /**
     *
     * @Author luokun
     * @Description 根据id修改商品类名
     * @Date 2020/7/8 10:05
     * @param id 商品id
     * @param name 商品类名
     * @return
     **/
    void update(Long id, String name);

    /**
     *
     * @Author luokun
     * @Description 根据id删除商品类
     * @Date 2020/7/8 10:05
     * @param id 商品类id
     * @return
     **/
    void delete(Long id);

    /**
     *
     * @Author luokun
     * @Description 根据bid获取对应品牌的商品类信息
     * @Date 2020/7/8 10:05
     * @param bid 品牌bid
     * @return
     **/
    List<Category> getByBid(Long bid);
}
