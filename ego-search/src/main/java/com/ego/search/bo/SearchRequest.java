package com.ego.search.bo;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName SearchRequest
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 17:49
 * @Version 1.0
 **/
@Data
public class SearchRequest {
    /**
     * 搜索关键词
     **/
    private String key;
    /**
     * 过滤条件
     **/
    private Map<String,String> filter;
    /**
     * 排序条件
     **/
    private String sortBy;
    /**
     * 当前页
     **/
    private Integer pageNo;
}
