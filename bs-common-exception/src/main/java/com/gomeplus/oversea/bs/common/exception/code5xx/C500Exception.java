package com.gomeplus.oversea.bs.common.exception.code5xx;


import com.gomeplus.oversea.bs.common.exception.RESTfull5xxBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class C500Exception extends RESTfull5xxBaseException implements Serializable {

    private static final long serialVersionUID = 3292618827942305707L;

    public C500Exception(String message) {
        super(message);
    }
}
