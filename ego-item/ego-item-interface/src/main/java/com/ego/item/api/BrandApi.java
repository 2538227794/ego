package com.ego.item.api;

import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.CacheRequest;
import java.util.List;

/**
 * @ClassNameCategoryApi
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/10/8 12:15
 * @Version 1.0
 **/
@RequestMapping("/brand")
public interface BrandApi {

    @GetMapping("/list/ids")
    ResponseEntity<List<Brand>> getBrandListByIds(@RequestParam("ids") List<Long> idList);
}
