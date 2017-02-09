package com.gomeplus.oversea.bs.common.exception.code4xx;


import com.gomeplus.oversea.bs.common.exception.RESTfull4xxBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
@ResponseStatus(HttpStatus.GONE)
public class C410Exception extends RESTfull4xxBaseException implements Serializable {


    private static final long serialVersionUID = 6479372425706561796L;

    public C410Exception(String message) {
        super(message);
    }
}
