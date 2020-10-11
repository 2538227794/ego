package com.ego.search.Service;

import com.ego.item.bo.SpuBo;
import com.ego.search.bo.SearchRequest;
import com.ego.search.bo.SearchResult;
import com.ego.search.pojo.Goods;

/**
 * @ClassName SearchService
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 2:33
 * @Version 1.0
 **/
public interface SearchService {

    /**
     * @Author luokun
     * @Description SpuBo对象转化为Goods
     * @Date  2020/9/27 2:40
     * @Param [spuBo]
     * @return com.ego.search.pojo.Goods
     **/
    Goods buildGoods(SpuBo spuBo);

    /**
     * @Author luokun
     * @Description 商品搜索分页
     * @Date  2020/9/27 18:00
     * @Param [searchRequest]
     * @return com.ego.search.bo.SearchResult
     **/
    SearchResult page(SearchRequest searchRequest);
}
