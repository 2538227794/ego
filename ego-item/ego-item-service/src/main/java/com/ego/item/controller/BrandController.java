package com.ego.item.controller;

import com.ego.commom.PageResult;
import com.ego.item.pojo.Brand;
import com.ego.item.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpStatus;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @ClassNameBrandController
 * @Descripiotn 品牌控制层
 * @Author luokun
 * @Date 2020/9/21 10:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/brand")
@Slf4j
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     *
     * @Author luokun
     * @Description 品牌分页
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/

    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> page(
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "descending",required = false) Boolean descending,
            @RequestParam(value = "key") String key
    ){
        try {
            //pageNo和pageSize是否有效
            if(pageNo.intValue()<=0||pageSize<=0){
                //响应400
                log.debug("pageNo或者pageSize无效：pageNo={}，pageSize={}",pageNo,pageSize);
                return ResponseEntity.badRequest().build();
            }
            PageResult<Brand> result=brandService.getPage(pageNo,pageSize,sortBy,descending,key);
            if(result==null|| CollectionUtils.isEmpty(result.getItems())){
                //响应404
                log.debug("根据条件pageNo={},pageSize={},sortBy={},descending={},key={}未查询到品牌",pageNo,pageSize,sortBy,descending,key);
                return ResponseEntity.notFound().build();
            }
            //响应200
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("根据条件pageNo={},pageSize={},sortBy={},descending={},key={}查询品牌出现异常：",e);
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description 新增品牌
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    @PostMapping
    public ResponseEntity<Void> save(@RequestParam("cids") Long[] cids,Brand brand){
        try {
            //新增品牌
            brandService.save(brand,cids);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("新增品牌异常：",e);
            e.printStackTrace();
        }
        //响应500
        return  ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description 修改品牌
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    @PutMapping
    public ResponseEntity<Void> update(@RequestParam("cids") Long[] cids,Brand brand){
        try {
            //修改品牌
            brandService.update(brand,cids);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("修改品牌异常：",e);
            e.printStackTrace();
        }
        //响应500
        return  ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description 删除品牌
     * @Date 2020/7/8 10:05
     * @param id 品牌id
     * @return
     **/
    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestParam("id") Long id){
        try {
            //id是否有效
            if(id.longValue()<0){
                //响应400
                log.debug("无效id={}",id);
                return ResponseEntity.badRequest().build();
            }
            //删除品牌
            brandService.deleteById(id);
            //响应200
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("删除商品出现异常：",e);
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Author luokun
     * @Description 根据商品类id获取品牌List
     * @Date  2020/9/23 21:47
     * @Param [categoryId]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.ego.item.pojo.Brand>>
     **/
    @GetMapping("/cid/{categoryId}")
    public ResponseEntity<List<Brand>> queryBrandListByCategoryId(@PathVariable("categoryId") Long categoryId ){
        //查询品牌
        List<Brand> brands=brandService.getBrandListByCategoryId(categoryId);
        //响应200
        return ResponseEntity.ok(brands);
    }
}
