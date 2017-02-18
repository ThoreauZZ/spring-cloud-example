package com.erdaoya.springcloud.common.exception.code3xx;


import com.erdaoya.springcloud.common.exception.RESTfull3xxBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * 301异常：Moved Permanently：资源或API被迁移到新的URL，API 更名应该使用该code
 * @author zhaozhou
 */
@ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
public class C301Exception extends RESTfull3xxBaseException implements Serializable {

    private static final long serialVersionUID = 2471232079442861956L;

    public C301Exception(String message) {
        super(message);
    }
}