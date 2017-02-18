package com.erdaoya.springcloud.client.web;

import com.erdaoya.springcloud.client.client.UserFeignClient;
import com.erdaoya.springcloud.client.client.UserRibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 2017/1/13
 * @author erdaoya
 * @since 1.0
 */
@RestController
@RequestMapping("/ext/trade")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public Object getUserByRibbon(String id) {
        return restTemplate.getForEntity("http://TRADE-SERVICE/trade/order?id=" + id, Object.class).getBody();
    }

}
