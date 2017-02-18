package com.erdaoya.springcloud.common.exception.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by shangshengfang on 2017/2/16.
 */
@Data
public class Error {
    String message;
    String code;
    List<ErrorDetail> errors;

}
