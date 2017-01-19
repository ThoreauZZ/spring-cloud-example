package com.erdaoya.springcloud.client.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 2016/12/25 21:43
 *
 * @author erdaoya
 */
@FeignClient(name = "user-service", fallback = UserFeignClient.HystrixClientFallback.class)
public interface UserFeignClient {


    @RequestMapping(method = RequestMethod.GET)
    Object getUser(@RequestParam(value = "id") String id);

    static class HystrixClientFallback implements UserFeignClient {

        @Override
        public Object getUser(@RequestParam(value = "id") String id) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("mesages", "Hystrix Fallback");
            return map;
        }
    }
}
