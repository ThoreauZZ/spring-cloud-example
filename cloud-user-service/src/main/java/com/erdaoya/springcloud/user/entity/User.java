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
    private Long id;
    private String loginName;
    private String nickName;
    private String password;
    private String mobile;
    private String email;
    private String gender;
    private Long registerTome;
    private Long updateTime;
}
