package com.ego.item.Controller;

import com.ego.commom.PageResult;
import com.ego.item.Service.BrandService;
import com.ego.item.pojo.Brand;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassNameBrandController
 * @Descripiotn
 * @Author luokun
 * @Date 2020/9/19 23:33
 * @Version 1.0
 **/
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     *
     * @Author luokun
     * @Description //获取分页数据
     * @Date 2020/7/8 10:05
     * @param
     * @return
     **/
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> page(
            @RequestParam(value = "page", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "rows", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "descending", defaultValue = "false") Boolean descending,
            @RequestParam(value = "key", required = false) String key) {
        try {
            if(pageNo.intValue()<0||pageSize.intValue()<0){
                //pageNo或pageSize无效，响应400
                return  ResponseEntity.badRequest().build();
            }
            //查询数据库
            PageResult<Brand> result=brandService.page(pageNo,pageSize,sortBy,descending,key);
            System.out.println(result.getItems().toString());
            if( CollectionUtils.isEmpty(result.getItems())){
                //没找品牌，响应404
                return ResponseEntity.notFound().build();
            }
            //响应200
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //响应500
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
    }

}
