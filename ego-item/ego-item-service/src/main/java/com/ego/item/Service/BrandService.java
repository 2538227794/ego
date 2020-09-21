package com.ego.item.Service;

import com.ego.commom.PageResult;
import com.ego.item.pojo.Brand;

/**
 * @ClassNameBrandService
 * @Descripiotn 品牌业务接口
 * @Author luokun
 * @Date 2020/9/19 23:37
 * @Version 1.0
 **/
public interface BrandService {
    /**
     *
     * @Author luokun
     * @Description 获取分页数据
     * @Date 2020/7/8 10:05
     * @param null
     * @return
     **/
    PageResult<Brand> page(Integer pageNo, Integer pageSize, String sortBy, Boolean descending, String key);
}
