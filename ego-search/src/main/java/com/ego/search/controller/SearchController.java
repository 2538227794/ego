package com.ego.search.controller;

import com.ego.search.Service.SearchService;
import com.ego.search.bo.SearchRequest;
import com.ego.search.bo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SearchController
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 17:39
 * @Version 1.0
 **/
@RestController
@Slf4j
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("/page")
    public ResponseEntity<SearchResult> page(@RequestBody SearchRequest searchRequest){
        SearchResult searchResult=searchService.page(searchRequest);
        if(searchResult==null){
            return ResponseEntity.badRequest().build();
        }
        if(CollectionUtils.isEmpty(searchResult.getItems())){
            log.debug("未找到查询商品：{}",searchRequest);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(searchResult);
    }
}
