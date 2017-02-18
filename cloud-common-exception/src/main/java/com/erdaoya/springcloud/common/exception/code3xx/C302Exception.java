package com.erdaoya.springcloud.common.exception.code3xx;

import com.erdaoya.springcloud.common.exception.RESTfull3xxBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * 302异常 ，Move temporarily：请求的资源临时从不同的 URI响应请求
 * @author erdaoya
 */
@ResponseStatus(HttpStatus.FOUND)
public class C302Exception extends RESTfull3xxBaseException implements Serializable {

    private static final long serialVersionUID = -4669887293221187463L;

    public C302Exception(String message) {
        super(message);
    }
}