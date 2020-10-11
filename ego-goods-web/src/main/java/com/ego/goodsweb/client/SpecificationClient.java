package com.ego.goodsweb.client;

import com.ego.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName
 * @Description //TODO
 * @Author luokun
 * @Date  2020/10/9 1:12
 * @Version 1.0
 **/
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {

}
