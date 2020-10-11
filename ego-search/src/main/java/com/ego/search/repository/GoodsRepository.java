package com.ego.search.repository;

import com.ego.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName GoodsRepository
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 2:18
 * @Version 1.0
 **/
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
