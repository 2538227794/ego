package com.ego.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ego.item.mapper.CategoryMapper;
import com.ego.item.pojo.Category;
import com.ego.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassNameCategoryServiceImpl
 * @Descripiotn 商品类业务实现
 * @Author luokun
 * @Date 2020/9/21 10:32
 * @Version 1.0
 **/
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> get(Long pid) {
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",pid);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return categories;
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void update(Long id, String name) {
        categoryMapper.updateNameByid(id,name);
    }

    @Override
    public void delete(Long id) {
        //删除商品类
        categoryMapper.deleteById(id);
        //删除中间表的商品类
        categoryMapper.deleteCategoryBrandById(id);
    }

    @Override
    public List<Category> getByBid(Long bid) {
        //获取品牌对应商品类id
        List<Long>ids=categoryMapper.getByBid(bid);
        if(ids.size()>0){
            //根据ids获取商品类
            List<Category> categories = categoryMapper.selectBatchIds(ids);
            return categories;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findListByIds(List<Long> idList) {
        List<Category> categories = categoryMapper.selectBatchIds(idList);
        return categories;
    }
}
