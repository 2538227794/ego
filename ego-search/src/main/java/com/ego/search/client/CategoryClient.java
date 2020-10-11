package com.ego.search.client;

import com.ego.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @ClassNameCategoryClient
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/10/8 12:11
 * @Version 1.0
 **/
@FeignClient("item-service")
@Component
public interface CategoryClient extends CategoryApi {
}
