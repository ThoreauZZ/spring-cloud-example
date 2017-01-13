package com.erdaoya.springcloud.client.web;

import com.erdaoya.springcloud.client.client.UserFeignClient;
import com.erdaoya.springcloud.client.client.UserRibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2017/1/13
 * @author erdaoya
 * @since 1.0
 */
@RestController
@RequestMapping("/client")
public class UserController {

    @Autowired
    private UserRibbonClient ribbonClient;

    @Autowired
    private UserFeignClient feignClient;

    @RequestMapping(value = "/feign", method = RequestMethod.GET)
    public Object getUserByFeign(String id) {
        return feignClient.getUser(id);
    }
    @RequestMapping(value = "/ribbon", method = RequestMethod.GET)
    public Object getUserByRibbon(String id) {
        return ribbonClient.getUser(id);
    }

}
