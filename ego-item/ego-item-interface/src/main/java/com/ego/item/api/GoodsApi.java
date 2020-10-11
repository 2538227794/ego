package com.ego.item.api;

import com.ego.item.bo.SpuBo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName GoodsApi
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 2:00
 * @Version 1.0
 **/
@RequestMapping("/goods")
public interface GoodsApi {

    /**
     * @Author luokun
     * @Description 批次获取上架商品数据
     * @Date  2020/9/27 15:01
     * @Param [key, saleable, page, rows]
     * @return org.springframework.http.ResponseEntity<com.ego.commom.PageResult<com.ego.item.bo.SpuBo>>
     **/
    @GetMapping("/spu/batchGoods")
    ResponseEntity<List<SpuBo>> queryPage(
            @RequestParam("page") Integer page,
            @RequestParam("rows")Integer rows
    );

    /**
     * @Author luokun
     * @Description 根据id查询商品
     * @Date  2020/10/9 1:06
     * @Param [id]
     * @return com.ego.item.bo.SpuBo
     **/
    @GetMapping("/spubo/{id}")
    SpuBo queryGoodsById(@PathVariable("id") Long id);
}
