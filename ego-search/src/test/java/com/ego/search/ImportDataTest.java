package com.ego.search;

import com.ego.commom.PageResult;
import com.ego.item.bo.SpuBo;
import com.ego.search.Service.SearchService;
import com.ego.search.client.GoodsClient;
import com.ego.search.pojo.Goods;
import com.ego.search.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @ClassName ImportDataTest
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 1:51
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EgoSearchService.class)
public class ImportDataTest {
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsRepository goodsRepository;
    /**
     * @Author luokun
     * @Description 将mysql数据转换到Elasticsearch
     * @Date  2020/9/27 2:05
     * @Param []
     * @return void
     **/
    @Test
    public void importDataFromMysql(){
        int size=0;
        int page=1;
        //分批次把mysql数据导入到es
        do{
            List<SpuBo> spuBoList = goodsClient.queryPage( page++, 100).getBody();
            //List<SpuBo>转化为List<Goods>
            List<Goods> goodsList = spuBoList.stream().map(spuBo -> searchService.buildGoods(spuBo)).collect(Collectors.toList());
            goodsRepository.saveAll(goodsList);
            size = spuBoList.size();
        }while (size==100);
    }

}
