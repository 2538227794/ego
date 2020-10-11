package com.ego.item.api;

import com.ego.item.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassNameCategoryApi
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/10/8 12:15
 * @Version 1.0
 **/
@RequestMapping("/category")
public interface CategoryApi {
    /**
     * @Author luokun
     * @Description idList批量查询Category
     * @Date  2020/10/8 12:25
     * @Param [idList]
     * @return java.util.List<com.ego.item.pojo.Category>
     **/
    @GetMapping("/list/ids")
    ResponseEntity<List<Category>> getCategoryListByIds(@RequestParam("ids") List<Long> idList);
}
