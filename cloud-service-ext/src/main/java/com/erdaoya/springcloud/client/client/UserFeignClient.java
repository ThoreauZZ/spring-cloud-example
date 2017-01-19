package com.erdaoya.springcloud.client.client;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
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
@FeignClient(name = "user-service", fallback = HystrixClientFallback.class)
public interface UserFeignClient {

    @RequestMapping(method = RequestMethod.GET)
    Object getUser(@RequestParam(value = "id") String id);


}

@Component
class HystrixClientFallback implements FallbackFactory<UserFeignClient> {


    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            public Object getUser(String id) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("mesages", "Hystrix Fallback");
                return map;
            }
        };

    }
}
