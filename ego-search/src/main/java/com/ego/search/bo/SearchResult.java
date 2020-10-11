package com.ego.search.bo;

import com.ego.commom.PageResult;
import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Category;
import com.ego.search.pojo.Goods;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SearchResult
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 17:47
 * @Version 1.0
 **/
@Data
public class SearchResult extends PageResult<Goods> {
    private List<Category> categories;
    private List<Brand> brands;
    private List<Map<String,Object>> specs;

}
