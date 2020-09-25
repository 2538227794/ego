package com.ego.item.controller;

import com.ego.commom.PageResult;
import com.ego.commom.enums.ExceptionEnum;
import com.ego.item.bo.SpuBo;
import com.ego.item.pojo.Spu;
import com.ego.item.service.GoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
