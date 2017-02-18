package com.erdaoya.springcloud.comx.schema.onError;

import com.erdaoya.springcloud.comx.utils.config.ConfigException;

/**
 * Created by xue on 1/12/17.
 */
public class StrategyException extends ConfigException {
    public StrategyException(String message) {
        super(message);
    }
    public StrategyException(Throwable throwable) {
        super(throwable);
    }
}
