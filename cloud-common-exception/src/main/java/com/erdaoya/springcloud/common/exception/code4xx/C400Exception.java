package com.erdaoya.springcloud.common.exception.code4xx;




import com.erdaoya.springcloud.common.exception.RESTfull4xxBaseException;
import com.erdaoya.springcloud.common.exception.util.ErrorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * <p>400异常 :Bad Request 请求格式错误.如Content-Type错误</p>
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class C400Exception extends RESTfull4xxBaseException implements Serializable {

    private static final long serialVersionUID = -5601106178975839965L;

    public C400Exception(String message) {
		super(message);
	}
    public C400Exception(String message,Error error){
        super(ErrorUtil.appendError(message,error));
    }
}