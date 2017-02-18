package com.erdaoya.springcloud.common.exception.code4xx;


import com.erdaoya.springcloud.common.exception.RESTfull4xxBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class C422Exception extends RESTfull4xxBaseException implements Serializable {
	private static final long serialVersionUID = -2312393803704717855L;
	public C422Exception(String message) {
		super(message);
	}
}
