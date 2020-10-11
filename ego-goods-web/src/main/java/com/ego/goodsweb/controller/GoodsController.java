package com.ego.goodsweb.controller;

import com.ego.goodsweb.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName
 * @Description
 * @Author luokun
 * @Date  2020/10/9 1:01
 * @Version 1.0
 **/
@Controller
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    @GetMapping("/item/{id}.html")
    public String item(@PathVariable("id") Long spuId, Model model) {
        //1.查询模型数据
        Map<String, Object> modelMap = goodsService.loadModel(spuId);
        model.addAllAttributes(modelMap);
        //2.生成静态页面
        goodsService.buildStaticHtml(spuId,modelMap);
        //3.渲染数据
        return "item";
    }
}
