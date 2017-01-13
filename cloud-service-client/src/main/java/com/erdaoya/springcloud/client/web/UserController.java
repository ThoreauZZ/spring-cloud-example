package com.erdaoya.springcloud.client.web;

import com.erdaoya.springcloud.client.client.UserFeignClient;
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
@RequestMapping("/feign/user")
public class UserController {

    @Autowired
    private UserFeignClient userClient;

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(String id) {
        return userClient.getUser(id);
    }
}
