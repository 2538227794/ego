package com.ego.item.Controller;

import com.ego.item.pojo.Category;
import com.ego.item.Service.CategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameCategoryContoller
 * @Descripiotn
 * @Author luokun
 * @Date 2020/9/19 18:45
 * @Version 1.0
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     *
     * @Author luokun
     * @Description //根据parentId查询商品类
     * @Date 2020/7/8 10:05
     * @param pid  parentId
     * @return
     **/
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam(value = "pid",defaultValue = "0") Long pid){
        try {
            if(pid==null||pid.longValue()<0){
                //parentId无效，响应400
                return ResponseEntity.badRequest().build();
            }
            //查询数据库
            List<Category> categories=categoryService.getCategoryListByParentId(pid);
            if(CollectionUtils.isEmpty(categories)){
                //返回结果集为空，响应404
                ResponseEntity.notFound().build();
            }
            //响应200
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description //新增商品
     * @Date 2020/7/8 10:05
     * @param category 商品
     * @return
     **/
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Category category){
        try {
            //新增数据库
            categoryService.add(category);
            //响应200
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description //根据id修改商品名
     * @Date 2020/7/8 10:05
     * @param id,name id，商品名
     * @return
     **/
    @PutMapping
    public ResponseEntity<Void> edit(@RequestParam("id") Long id,@RequestParam("name") String name){
        try {
            if(id==null||id.longValue()<0|| StringUtils.isBlank(name)){
                //id或者name参数无效，响应400
                return ResponseEntity.badRequest().build();
            }
            //操作数据库，编辑商品名
            categoryService.update(id,name);
            //响应200
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description //根据id删除商品
     * @Date 2020/7/8 10:05
     * @param id 商品id
     * @return
     **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        try {
            if(id==null||id.longValue()<0){
                //id无效，响应400
                return ResponseEntity.badRequest().build();
            }
            //删除数据库商品
            categoryService.delete(id);
            //响应200
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

}
