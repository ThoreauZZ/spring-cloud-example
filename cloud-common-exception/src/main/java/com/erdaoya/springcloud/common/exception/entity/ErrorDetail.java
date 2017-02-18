package com.erdaoya.springcloud.common.exception.entity;

import lombok.Data;

/**
 * Created by shangshengfang on 2017/2/16.
 */
@Data
public class ErrorDetail {
    String domain;
    String reason;
    String message;
}
