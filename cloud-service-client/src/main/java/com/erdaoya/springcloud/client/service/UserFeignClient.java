package com.erdaoya.springcloud.client.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 *@author erdaoya
 *@data 2016/12/25 21:43
 */
@FeignClient("user-service")
public interface UserFeignClient {
    Object getUser(@RequestParam String id);
}
