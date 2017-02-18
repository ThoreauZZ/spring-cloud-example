package com.erdaoya.springcloud.common.exception.code4xx;



import com.erdaoya.springcloud.common.exception.RESTfull4xxBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class C405Exception extends RESTfull4xxBaseException implements Serializable {

	private static final long serialVersionUID = -779530377325082078L;

	public C405Exception(String message) {
		super(message);
	}
}