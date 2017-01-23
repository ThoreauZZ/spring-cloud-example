package com.erdaoya.springcloud.gateway.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 2017/1/23
 *
 * @author erdaoya
 * @since 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel<T> {
    /**
     * message,error message
     */
    private String message = "";
    /**
     * response data
     */
    private T data;

    /**
     * 4xx error data
     */
    private T error;

    /**
     * debug mode
     */
    private String debug;

    public ResponseModel() {
    }

    public ResponseModel(T data) {
        this.data = data;
    }

    public ResponseModel(String message) {
        if (null != message) {
            this.message = message;
        }
    }

    public void setMessage(String message) {
        if (null != message) {
            this.message = message;
        }
    }

    public void setData(T data) {
        if (null != data) {
            this.data = data;
        }
    }

    public void setError(T error) {
        if (null != error) {
            this.error = error;
        }
    }
}
