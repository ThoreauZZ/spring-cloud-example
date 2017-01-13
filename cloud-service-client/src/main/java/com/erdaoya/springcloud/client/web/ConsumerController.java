package com.erdaoya.springcloud.client.web;

import com.erdaoya.springcloud.client.service.UserRibbonClient;
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
@RequestMapping("/user/user")
public class ConsumerController {

    @Autowired
    private UserRibbonClient userRibbonClient;

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(String id) {
        return userRibbonClient.getUser(id);
    }
}
