package com.ego.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ego.commom.PageResult;
import com.ego.commom.enums.ExceptionEnum;
import com.ego.commom.exception.EgoException;
import com.ego.item.mapper.BrandMapper;
import com.ego.item.pojo.Brand;
import com.ego.item.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassNameBrandServiceImpl
 * @Descripiotn 品牌业务接口实现
 * @Author luokun
 * @Date 2020/9/21 10:58
 * @Version 1.0
 **/
@Service
@Transactional
@Slf4j
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> getPage(Integer pageNo, Integer pageSize, String sortBy, Boolean descending, String key) {
        QueryWrapper<Brand> queryWrapper=new QueryWrapper<>();
        //过滤条件
        if (StringUtils.isNotBlank(key)){
            queryWrapper.eq("letter",key).or().like("name",key);
        }
        //排序条件
        if(descending!=null&&StringUtils.isNotBlank(sortBy)){
            queryWrapper.orderBy(true,!descending,sortBy);
        }
        Page<Brand> page = brandMapper.selectPage(new Page<>(pageNo, pageSize), queryWrapper);
        return new PageResult<Brand>(page.getTotal(),page.getRecords());
    }

    @Override
    public void save(Brand brand, Long[] cids) {
        //新增品牌
        brandMapper.insert(brand);
        //新增品牌商品类中间表
        for (Long cid : cids) {
            brandMapper.addBrandIdCategoryId(brand.getId(),cid);
        }
    }

    @Override
    public void update(Brand brand, Long[] cids) {
        //修改品牌
        brandMapper.updateById(brand);
        //修改品牌商品类中间表
        for (Long cid : cids) {
            brandMapper.deleteBrandIdCategoryId(brand.getId());
        }
        for (Long cid : cids) {
            brandMapper.addBrandIdCategoryId(brand.getId(),cid);
        }
    }

    @Override
    public void deleteById(Long id) {
        //删除中间表
        brandMapper.deleteBrandIdCategoryId(id);
        //删除品牌
        brandMapper.deleteById(id);
    }

    @Override
    public List<Brand> getBrandListByCategoryId(Long categoryId) {
        try {
            //商品类id获取品牌ids
            List<Long> brandIds =brandMapper.selectBrandIdCategoryId(categoryId);
//            if (CollectionUtils.isEmpty(brandIds)){
//                throw new EgoException();
//            }
            //品牌ids获取品牌
            List<Brand> brands = brandMapper.selectBatchIds(brandIds);
            return brands;
        }  catch (Exception e){
            log.error("品牌查询异常{}",e);
            throw new EgoException(ExceptionEnum.BRAND_NOT_FOUND);
        }
    }

}
