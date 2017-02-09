package com.gomeplus.oversea.bs.common.exception.code4xx;


import com.gomeplus.oversea.bs.common.exception.RESTfull4xxBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * 2017/2/9
 * @author  erdaoay
 * @since   1.0
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class C401Exception extends RESTfull4xxBaseException implements Serializable {

    private static final long serialVersionUID = -8923853041057121290L;

    public C401Exception(String message) {
        super(message);
    }
}
