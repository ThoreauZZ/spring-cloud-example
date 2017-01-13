package com.erdaoya.springcloud.user.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 2017/1/13
 * @author  erdaoya
 * @since   1.0
 */
@Data
public class User {
    private String id;
    private String username;
    private String password;
}
