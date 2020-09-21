package com.ego.item.Service;

import com.ego.item.pojo.Category;

import java.util.List;

/**
 * @ClassNameCategoryService
 * @Descripiotn
 * @Author luokun
 * @Date 2020/9/19 18:47
 * @Version 1.0
 **/
public interface CategoryService {
    /**
     *
     * @Author luokun
     * @Description //根据parentId查询商品类
     * @Date 2020/7/8 10:05
     * @param pid   parentId
     * @return
     **/
    List<Category> getCategoryListByParentId(Long pid);

    /**
     *
     * @Author luokun
     * @Description //新增商品
     * @Date 2020/7/8 10:05
     * @param category 商品对象
     * @return
     **/
    void add(Category category);

    /**
     *
     * @Author luokun
     * @Description //根据id修改商品名
     * @Date 2020/7/8 10:05
     * @param id,name 商品id，商品名
     * @return
     **/
    void update(Long id, String name);

    /**
     *
     * @Author luokun
     * @Description 根据id删除商品
     * @Date 2020/7/8 10:05
     * @param id 商品id
     * @return
     **/
    void delete(Long id);
}
