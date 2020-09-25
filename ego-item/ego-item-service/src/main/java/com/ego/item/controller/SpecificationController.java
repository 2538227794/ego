package com.ego.item.controller;

import com.ego.item.pojo.Specification;
import com.ego.item.service.SpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SpecificationController
 * @Descripiotn 商品规格控制层
 * @Author luokun
 * @Date 2020/9/23 14:07
 * @Version 1.0
 **/
@RestController
@RequestMapping("/spec")
@Slf4j
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * @Author luokun
     * @Description 依据商品类id查询商品类规格json
     * @Date  2020/9/23 14:15
     * @Param [categoryId] 商品类id
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @GetMapping("/{categoryId}")
    public ResponseEntity<String> querySpecifications(@PathVariable("categoryId") Long categoryId){
        //查询商品规格
        String specifications = specificationService.getSpecifications(categoryId);
        return ResponseEntity.ok(specifications);
    }

    /**
     * @Author luokun
     * @Description 新增商品类规格
     * @Date  2020/9/23 14:32
     * @Param [specification]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @PostMapping
    public ResponseEntity<Void> save(Specification specification){
        //新增商品类规格
        specificationService.add(specification);
        //响应200
        return ResponseEntity.ok().build();
    }

    /**
     * @Author luokun
     * @Description 修改商品类规格
     * @Date  2020/9/23 15:57
     * @Param [specification]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @PutMapping
    public ResponseEntity<Void> update(Specification specification){
        //新增商品类规格
        specificationService.update(specification);
        //响应200
        return ResponseEntity.ok().build();
    }

}
