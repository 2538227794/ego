package com.ego.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ego.commom.PageResult;
import com.ego.commom.enums.ExceptionEnum;
import com.ego.commom.exception.EgoException;
import com.ego.item.bo.SpuBo;
import com.ego.item.mapper.BrandMapper;
import com.ego.item.mapper.CategoryMapper;
import com.ego.item.mapper.SpuMaaper;
import com.ego.item.pojo.Category;
import com.ego.item.pojo.Spu;
import com.ego.item.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName GoodsServiceImpl
 * @Descripiotn 商品业务实现
 * @Author luokun
 * @Date 2020/9/23 16:22
 * @Version 1.0
 **/
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpuMaaper spuMaaper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<SpuBo> getPaeg(String key, Boolean saleable, Integer page, Integer rows) {
        try {
            //分页查询
            QueryWrapper<Spu> queryWarry=new QueryWrapper<>();
            //过滤条件
            if(StringUtils.isNotBlank(key)){
                queryWarry.like("title",key);
            }
            //上架条件
            if(saleable!=null){
                queryWarry.eq("saleable",saleable);
            }
            Page<Spu> spuPage = spuMaaper.selectPage(new Page<>(page, rows), queryWarry);
            //Spu转换为SpuBo
            List<SpuBo> spuBoList = spuPage.getRecords().stream().map(spu -> {
                SpuBo spuBo = new SpuBo();
                spuBo.setId(spu.getId());
                spuBo.setTitle(spu.getTitle());
                spuBo.setSaleable(spu.getSaleable());
                //cid1,cid2,cid3获取商品类名
                List<Long> ids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
                List<Category> categories = categoryMapper.selectBatchIds(ids);
                List<String> names = categories.stream().map(category -> category.getName()).collect(Collectors.toList());
                String categoryNames = StringUtils.join(names,",");
                spuBo.setCategoryNames(categoryNames);
                //BrandId获取品牌名
                String BrandName = brandMapper.selectById(spu.getBrandId()).getName();
                spuBo.setBrandName(BrandName);
                return spuBo;
            }).collect(Collectors.toList());
            return new PageResult<>(spuPage.getTotal(),spuBoList);
        } catch (Exception e) {
            log.error("SPU分页异常：",e);
            throw new EgoException(ExceptionEnum.SPU_PAGE_EXCEPTION);
        }
    }
}
