package com.ego.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ego.commom.PageResult;
import com.ego.item.bo.SpuBo;
import com.ego.item.pojo.Spu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName SpuMaaper
 * @Descripiotn 标准产品mapper接口
 * @Author luokun
 * @Date 2020/9/23 14:03
 * @Version 1.0
 **/
public interface SpuMapper extends BaseMapper<Spu> {

    /**
     * @Author luokun
     * @Description 查询分页数据
     * @Date  2020/9/27 15:23
     * @Param [key, saleable, beginRow, rows]
     * @return com.ego.commom.PageResult<com.ego.item.bo.SpuBo>
     **/
    List<SpuBo> selectPage(@Param("key") String key,@Param("saleable") Boolean saleable, @Param("beginRow") Integer beginRow,@Param("rows") Integer rows);

    SpuBo selectSpuBoById(Long id);
}
