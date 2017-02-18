package com.gomeplus.comx.schema.onError;

import com.gomeplus.comx.schema.datadecor.decors.UnknownDecorTypeException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;

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
