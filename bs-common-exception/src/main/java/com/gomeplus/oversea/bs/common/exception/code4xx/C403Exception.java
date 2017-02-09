package com.gomeplus.oversea.bs.common.exception.code4xx;


import com.gomeplus.oversea.bs.common.exception.RESTfull4xxBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * 2017/2/9
 *
 * @author erdaoya
 * @since 1.0
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class C403Exception extends RESTfull4xxBaseException implements Serializable {

    private static final long serialVersionUID = 4956769958820573586L;

    public C403Exception(String message) {
        super(message);
    }
}