package com.erdaoya.springcloud.user.web;

import com.erdaoya.springcloud.user.dao.UserDao;
import com.erdaoya.springcloud.user.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.coyote.http11.Constants.a;


@RestController
@Slf4j
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @ApiOperation(value="get user by id", notes="")
    @ApiImplicitParam(name = "id", value = "user id ", required = true, paramType = "query")
    @RequestMapping(method = RequestMethod.GET)
    public User doGet(@RequestParam String id) {
        return userDao.selectUserById(id);
    }

    @ApiOperation(value="create user", notes="")
    @ApiImplicitParam(name = "user", value = "user vo ", required = true, dataType = "User")
    @RequestMapping(method = RequestMethod.POST)
    public void doGet(User User) {
        userDao.insertUser(User);
    }
}
