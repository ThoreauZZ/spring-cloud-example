package com.gomeplus.comx.schema.onError;

import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.schema.datadecor.DecorException;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;

/**
 * Created by xue on 1/12/17.
 */
public class ByDefaultStrategy extends Strategy {
    public static final String DEFAULT_VALUE_FIELD = "defaultValue";
    protected Config conf;
    public ByDefaultStrategy(Config conf){
        this.conf = conf;
    }

    @Override
    public Object handleSourceException(Exception ex, Context context) throws Exception{
        context.getLogger().error("OnError ByDefault Strategy, caught exception, return value by default. error:" + ex.getMessage() + "; class:" + ex.getClass());
        return this.conf.sub(DEFAULT_VALUE_FIELD).rawData();
    }

    @Override
    public Object handleDecorException(Exception ex, Context context, Object data) throws ConfigException{
        context.getLogger().error("OnError ByDefault Strategy, caught exception, and throw exception. error:" + ex.getMessage() + "; class:" + ex.getClass());
        throw new StrategyException("OnError strategy 'ByDefault' is not available on Decor node");
    }
}

