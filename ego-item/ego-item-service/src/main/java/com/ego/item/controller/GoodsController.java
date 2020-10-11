package com.ego.item.controller;

import com.ego.commom.PageResult;
import com.ego.commom.enums.ExceptionEnum;
import com.ego.item.bo.SpuBo;
import com.ego.item.pojo.Spu;
import com.ego.item.service.GoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GoodsController
 * @Descripiotn 商品控制层
 * @Author luokun
 * @Date 2020/9/23 16:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    //http://api.ego.com/api/item/goods/spu/page?key=&saleable=true&page=1&rows=5

    /**
     * @Author luokun
     * @Description 商品列表查询
     * @Date  2020/9/27 15:03
     * @Param [key, saleable, page, rows]
     * @return org.springframework.http.ResponseEntity<com.ego.commom.PageResult<com.ego.item.bo.SpuBo>>
     **/
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuBo>> queryPage(
            @RequestParam("key") String key,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam("page") Integer page,
            @RequestParam("rows")Integer rows
    ){
        //分页查询
        PageResult<SpuBo> result=goodsService.getPaeg(key,saleable,page,rows);
        if(result==null||CollectionUtils.isNotEmpty(result.getItems())){
            //响应200
            return ResponseEntity.ok(result);
        }
        //响应404
        return ResponseEntity.notFound().build();
    }

    /**
     * @Author luokun
     * @Description 批次获取上架商品数据
     * @Date  2020/9/27 15:12
     * @Param [page, rows]
     * @return java.util.List<com.ego.item.bo.SpuBo>
     **/
    @GetMapping("/spu/batchGoods")
    public ResponseEntity<List<SpuBo>> queryPage(
            @RequestParam("page") Integer page,
            @RequestParam("rows")Integer rows
    ){
        //分页查询
        List<SpuBo> spuBoList=goodsService.getPage(page,rows);
        if(CollectionUtils.isNotEmpty(spuBoList)){
            //响应200
            return ResponseEntity.ok(spuBoList);
        }
        //响应404
        return ResponseEntity.notFound().build();
    }

    /**
     * @Author luokun
     * @Description
     * @Date  2020/10/9 1:07
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.ego.item.bo.SpuBo>
     **/
    @GetMapping("/spubo/{id}")
    public ResponseEntity<SpuBo> queryGoodsById(@PathVariable("id") Long id){
        SpuBo spuBo=this.goodsService.queryGoodsById(id);
        if (spuBo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(spuBo);
    }
}
