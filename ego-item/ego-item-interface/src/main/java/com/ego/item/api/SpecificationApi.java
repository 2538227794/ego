package com.ego.item.api;

import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Specification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName
 * @Description //TODO
 * @Author luokun
 * @Date  2020/10/8 13:59
 * @Version 1.0
 **/
@RequestMapping("/spec")
public interface SpecificationApi {

    /**
     * @Author luokun
     * @Description 依据商品类id查询商品类规格json
     * @Date  2020/9/23 14:15
     * @Param [categoryId] 商品类id
     * @return org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @GetMapping("/{categoryId}")
    ResponseEntity<String> querySpecifications(@PathVariable("categoryId") Long categoryId);
}
