package com.erdaoya.springcloud.common.exception.code4xx;


import com.erdaoya.springcloud.common.exception.RESTfull4xxBaseException;
import com.erdaoya.springcloud.common.exception.util.ErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
@ResponseStatus(HttpStatus.CONFLICT)
public class C409Exception extends RESTfull4xxBaseException implements Serializable {

	private static final long serialVersionUID = 5041250399945421364L;

	public C409Exception(String message) {
		super(message);
	}
	public C409Exception(String message,Error error){
		super(ErrorUtil.appendError(message,error));
	}
}
