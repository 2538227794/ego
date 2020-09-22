package com.ego.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ego.item.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @ClassNameCategoryMapper
 * @Descripiotn 商品类mapper接口
 * @Author luokun
 * @Date 2020/9/21 10:33
 * @Version 1.0
 **/
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     *
     * @Author luokun
     * @Description 根据商品类id修改商品类名
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    @Update("update tb_category set name=#{name} where id=#{id}")
    void updateNameByid(@Param("id") Long id,@Param("name") String name);

    /**
     *
     * @Author luokun
     * @Description 根据商品类id删除中间表tb_category_brand
     * @Date 2020/7/8 10:05
     * @param id 商品类id
     * @return
     **/
    @Delete("delete from tb_category_brand where category_id=#{category_id}")
    void deleteCategoryBrandById(@Param("category_id") Long id);

    /**
     *
     * @Author luokun
     * @Description 根据bid在中间表获取品牌的商品类ids
     * @Date 2020/7/8 10:05
     * @param bid 品牌bid
     * @return
     **/
    @Select("select category_id from tb_category_brand where brand_id=#{brand_id}")
    List<Long> getByBid(@Param("brand_id") Long bid);
}
