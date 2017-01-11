package com.erdaoya.springcloud.user.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author erdaoya
 * @date 16/12/25 下午5:54
 */
@Data
public class User {
    private String id;
    private String username;
    private String password;
}
