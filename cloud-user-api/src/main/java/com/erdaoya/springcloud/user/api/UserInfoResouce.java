package com.erdaoya.springcloud.user.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 2017/1/13
 * @author erdaoya
 * @since 1.0
 */
@FeignClient(value = "user-service")
@RequestMapping("/user/userInfo")
public interface UserInfoResouce {

    @GetMapping
    Object doGet(@RequestParam(name = "userId") Long userId);
}
