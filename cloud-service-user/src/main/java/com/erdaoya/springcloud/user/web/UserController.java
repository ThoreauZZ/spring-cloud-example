package com.erdaoya.springcloud.user.web;

import com.erdaoya.springcloud.common.exception.code4xx.C404Exception;
import com.erdaoya.springcloud.user.dao.UserDao;
import com.erdaoya.springcloud.user.entity.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Slf4j
@RequestMapping("/user/personalInfo")
public class UserController {

    @Autowired
    private UserDao userDao;

    @ApiOperation(value = "get userInfo by id", notes = "")
    @ApiImplicitParam(name = "id", value = "user id ", required = true, paramType = "query")
    @RequestMapping(method = RequestMethod.GET)
    public User doGet(@RequestParam Long id) {
        User user = userDao.selectUserById(id);
        if(user == null){
            throw new C404Exception("user not found");
        }
        return user;
    }

    @ApiOperation(value = "create userInfo", notes = "")
    @ApiImplicitParam(name = "user", value = "user vo ", required = true, dataType = "User")
    @RequestMapping(method = RequestMethod.POST)
    public void doPost(User User) {
        userDao.insertUser(User);
    }
}
