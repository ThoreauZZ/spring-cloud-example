package com.erdaoya.springcloud.client.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
    public Object getUser(Long id) {
        return restTemplate.getForEntity("http://USER-SERVICE/user/persionalInfo?id=" + id, Object.class).getBody();
    }

    public Object getUserFallback(String id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mesages", "Hystrix Fallback");
        return map;
    }

    @HystrixCommand(
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")},
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "101"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            })
    public String getUserById(String id) {
        return null;
    }
}
