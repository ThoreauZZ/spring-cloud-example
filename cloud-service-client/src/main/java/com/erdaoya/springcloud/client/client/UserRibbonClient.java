package com.erdaoya.springcloud.client.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 2017/1/13
 *
 * @author erdaoya
 * @since 1.0
 */
@Service
public class UserRibbonClient {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getUserFallback")
    public String getUser(String id) {
        return restTemplate.getForEntity("http://USER-SERVICE/user/user?id=" + id, String.class).getBody();
    }

    public String getUserFallback(String id) {
        return "error";
    }
}
