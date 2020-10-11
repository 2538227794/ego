package com.ego.search.client;

import com.ego.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @ClassName GoodsClient
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/9/27 1:56
 * @Version 1.0
 **/
@FeignClient("item-service")
@Component
public interface GoodsClient extends GoodsApi {
}
