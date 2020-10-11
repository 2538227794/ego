package com.ego.search.client;

import com.ego.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @ClassNamespecificationClient
 * @Descripiotn //TODO
 * @Author luokun
 * @Date 2020/10/8 13:55
 * @Version 1.0
 **/
@FeignClient("item-service")
@Component
public interface SpecificationClient extends SpecificationApi {
}
