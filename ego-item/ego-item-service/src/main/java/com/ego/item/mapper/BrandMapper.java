package com.ego.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ego.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassNameBrandMapper
 * @Descripiotn 品牌mapper接口
 * @Author luokun
 * @Date 2020/9/21 10:59
 * @Version 1.0
 **/
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     *
     * @Author luokun
     * @Description 新增数据tb_category_brand中间表
     * @Date 2020/7/8 10:05
     * @param id  category_id
     * @param cid brand_id
     * @return
     **/
    @Insert("insert into tb_category_brand(category_id, brand_id) values (#{cid},#{bid})")
    void addBrandIdCategoryId(@Param("bid") Long id,@Param("cid") Long cid);

    /**
     *
     * @Author luokun
     * @Description 删除数据tb_category_brand中间表
     * @Date 2020/7/8 10:05
     * @param id 品牌id
     * @return
     **/
    @Delete("delete from tb_category_brand where brand_id=#{brand_id}")
    void deleteBrandIdCategoryId(@Param("brand_id") Long id);
}
