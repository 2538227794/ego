package com.ego.item.controller;

import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Category;
import com.ego.item.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassNameCategory
 * @Descripiotn 商品类控制层
 * @Author luokun
 * @Date 2020/9/21 10:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     *
     * @Author luokun
     * @Description 根据pid查询商品类
     * @Date 2020/7/8 10:05
     * @param pid 父id
     * @return
     **/
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryListByPid(@RequestParam("pid") Long pid){
        try {
            //parentId是否有效
            if(pid.longValue()<0||pid==null){
                //响应400
                log.debug("pid值无效：{}",pid);
                return ResponseEntity.badRequest().build();
            }
            List<Category> categories=categoryService.get(pid);
            //是否查找商品类
            if(categories==null||categories.size()==0){
                //响应404
                log.debug("未找到pid={}的商品类",pid);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            log.error("根据pid查询商品类出现异常：",e);
            e.printStackTrace();

        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description 新增商品类
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Category category) {
        try {
            //新增商品类
            categoryService.add(category);
            //响应200
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("新增商品类异常：",e);
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description 根据id修改商品类名
     * @Date 2020/7/8 10:05
     * @param id  商品类id
     * @param name  商品类名
     * @return
     **/
    @PutMapping
    public ResponseEntity<Void> update(@RequestParam("id") Long id,@RequestParam("name") String name){
        try {
            //修改商品类
            categoryService.update(id,name);
            //响应200
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("修改商品出现异常：",e);
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /***
     *
     * @Author luokun
     * @Description 根据id删除商品类
     * @Date 2020/7/8 10:05
     * @param id 商品类id
     * @return
     **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id")Long id){
        try {
            //删除商品类
            categoryService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("删除商品类异常：{}",e);
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     *
     * @Author luokun
     * @Description 根据bid获取对应品牌的商品类信息
     * @Date 2020/7/8 10:05
     * @param bid 品牌bid
     * @return
     **/
    @GetMapping("/bid/{id}")
    public ResponseEntity<List<Category>> queryById(@PathVariable("id")Long bid){
        try {
            //id是否有效
            if(bid.longValue()<0){
                //响应400
                log.debug("id无效",bid);
                return ResponseEntity.badRequest().build();
            }
            //获取品牌信息
            List<Category> categories=categoryService.getByBid(bid);
            //响应200
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            log.error("查询品牌异常：",e);
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Author luokun
     * @Description idList批量查询Category
     * @Date  2020/10/8 12:25
     * @Param [idList]
     * @return java.util.List<com.ego.item.pojo.Category>
     **/
    @GetMapping("/list/ids")
    ResponseEntity<List<Category>> getCategoryListByIds(@RequestParam("ids") List<Long> idList){
        List<Category> categories = categoryService.findListByIds(idList);
        return ResponseEntity.ok(categories);
    }
}
