package com.erdaoya.springcloud.client.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 *@author erdaoya
 *@data 2016/12/25 21:43
 */
@FeignClient("user-service")
public interface UserFeignClient {
	@RequestMapping(method = RequestMethod.GET)
    Object getUser(@RequestParam( value= "id" ) String id);
}
