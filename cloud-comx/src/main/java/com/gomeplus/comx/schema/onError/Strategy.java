package com.gomeplus.comx.schema.onError;

import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.schema.datadecor.DecorException;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;

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
