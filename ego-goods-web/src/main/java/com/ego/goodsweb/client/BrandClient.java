package com.ego.goodsweb.client;

import com.ego.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName
 * @Description
 * @Author luokun
 * @Date  2020/10/9 1:05
 * @Version 1.0
 **/
@FeignClient("item-service")
public interface BrandClient extends BrandApi {

}
