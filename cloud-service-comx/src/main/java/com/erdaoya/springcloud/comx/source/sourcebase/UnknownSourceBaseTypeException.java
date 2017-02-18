package com.erdaoya.springcloud.comx.source.sourcebase;

import com.erdaoya.springcloud.comx.utils.config.ConfigException;

/**
 * Created by xue on 12/23/16.
 */
public class UnknownSourceBaseTypeException extends ConfigException {
    public UnknownSourceBaseTypeException(String message) {
        super(message);
    }
}
