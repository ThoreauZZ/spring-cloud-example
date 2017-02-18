package com.erdaoya.springcloud.comx.schema.onError;

import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.schema.datadecor.DecorException;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.config.Config;

/**
 * Created by xue on 1/12/17.
 */
public abstract class Strategy {
    public static Strategy fromConf(Config config) throws StrategyException {
        String type = config.str("type", "fail");
        switch (type) {
            case "ignore":      return IgnoreStrategy.getInstance();
            case "fail":        return FailStrategy.getInstance();
            case "byDefault":   return new ByDefaultStrategy(config);
            default:            throw new StrategyException("unkown strategy, type:" + type);
        }
    }

    abstract public Object handleSourceException(Exception ex, Context context) throws Exception;
    abstract public Object handleDecorException(Exception ex, Context context, Object data) throws ConfigException, SourceException, DecorException;
}
