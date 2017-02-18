package com.erdaoya.springcloud.comx.schema.datadecor.decors;


import com.erdaoya.springcloud.comx.utils.config.ConfigException;

/**
 * Created by xue on 12/20/16.
 */
public class UnknownDecorTypeException extends ConfigException {
    public UnknownDecorTypeException(String message) {
        super(message);
    }
}
