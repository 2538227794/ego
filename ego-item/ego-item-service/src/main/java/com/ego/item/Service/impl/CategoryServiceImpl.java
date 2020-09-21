package com.ego.item.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ego.item.Service.CategoryService;
import com.ego.item.mapper.CategoryMapper;
import com.ego.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameCategoryServiceImpl
 * @Descripiotn
 * @Author luokun
 * @Date 2020/9/19 18:48
 * @Version 1.0
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryListByParentId(Long pid) {
        Map<String,Object> map=new HashMap<>();
        map.put("parent_id",pid);
        return categoryMapper.selectByMap(map);
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    //TODO
    @Override
    public void update(Long id, String name) {
//        categoryMapper.
    }

    @Override
    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }
}
