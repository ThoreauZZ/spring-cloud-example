package com.erdaoya.springcloud.common.exception;

import java.io.Serializable;

/**
 * 2017/2/9
 *
 * @author erdaoay
 * @since 1.0
 */
public class RestfullBaseException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 90291629754433147L;

    RestfullBaseException(String message) {
        super(message);
    }

}
