package com.ego.item.service.impl;

import com.ego.commom.enums.ExceptionEnum;
import com.ego.commom.exception.EgoException;
import com.ego.item.mapper.SpecificationMapper;
import com.ego.item.pojo.Specification;
import com.ego.item.service.SpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.stereotype.Service;

/**
 * @ClassName SpecificationServiceImpl
 * @Descripiotn 商品类规格业务实现
 * @Author luokun
 * @Date 2020/9/23 14:17
 * @Version 1.0
 **/
@Service
@Slf4j
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public String getSpecifications(Long categoryId) {
        try {
            Specification specification = specificationMapper.selectById(categoryId);
            return specification.getSpecifications();
        } catch (Exception e) {
            log.error("查询商品类规格异常:{}",e);
            throw new EgoException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
    }

    @Override
    public void add(Specification specification) {
        try {
            specificationMapper.insert(specification);
        } catch (Exception e) {
            log.error("新增商品类规格失败:{}",e);
            throw new EgoException(ExceptionEnum.SPEC_GROUP_CREATE_FAILED);
        }
    }

    @Override
    public void update(Specification specification) {
        try {
            specificationMapper.updateById(specification);
        } catch (Exception e) {
            log.error("修改商品类规格失败:{}",e);
            throw new EgoException(ExceptionEnum.UPDATE_SPEC_GROUP_FAILED);
        }
    }
}
