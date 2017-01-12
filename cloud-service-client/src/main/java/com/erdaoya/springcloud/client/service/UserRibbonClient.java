package com.erdaoya.springcloud.client.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 *@author erdaoya
 *@data 2016/12/25 21:43
 */
@Service
public class UserRibbonClient {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getUserFallback")
    public String getUser(String id) {
        return restTemplate.getForEntity("http://USER-SERVICE/user/user?id="+id, String.class).getBody();
    }
    public String getUserFallback(String id) {
        return "error";
    }
}
