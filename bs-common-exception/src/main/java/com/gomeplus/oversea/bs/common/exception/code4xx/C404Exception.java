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
@ResponseStatus(HttpStatus.NOT_FOUND)
public class C404Exception extends RESTfull4xxBaseException implements Serializable {

    private static final long serialVersionUID = 4127623155490726561L;

    public C404Exception(String message) {
        super(message);
    }
}