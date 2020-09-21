package com.ego.item.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ego.commom.PageResult;
import com.ego.item.Service.BrandService;
import com.ego.item.mapper.BrandMapper;
import com.ego.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassNameBrandServiceImpl
 * @Descripiotn
 * @Author luokun
 * @Date 2020/9/19 23:37
 * @Version 1.0
 **/
@Service
public class  BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> page(Integer pageNo, Integer pageSize, String sortBy, Boolean descending, String key) {
        QueryWrapper<Brand> queryWrapper=new QueryWrapper();
        //过滤条件
        if(StringUtils.isNotBlank(key)){
            //模糊查询name or  精确查询letter
            queryWrapper.like("name",key).or().eq("letter",key);
        }
        //排序条件
        if(StringUtils.isNotBlank(sortBy)&&descending!=null){
            queryWrapper.orderBy(true,!descending,sortBy);
        }
        Page<Brand> page = brandMapper.selectPage(new Page<>(pageNo, pageSize), queryWrapper);
        PageResult<Brand> result = new PageResult<>((long) page.getRecords().size(),page.getRecords());
        return result;
    }
}
