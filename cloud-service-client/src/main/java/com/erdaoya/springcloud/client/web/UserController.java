package com.erdaoya.springcloud.client.web;

import com.erdaoya.springcloud.client.service.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *@author erdaoya
 *@data 2016/12/25 21:25
 */
@RestController
@RequestMapping("/feign/user")
public class UserController {

    @Autowired
    private UserFeignClient userClient;

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(String id) {
        return userClient.getUser(id);
    }
}
