package com.erdaoya.springcloud.common.exception.entity;

import lombok.Data;

import java.lang.*;

/**
 * Created by shangshengfang on 2017/2/17.
 */
@Data
public class FullError {
    private String message;
    private java.lang.Error error;
}
