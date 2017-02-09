package com.gomeplus.comx.schema.onError;

import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.schema.datadecor.DecorException;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;

/**
 * Created by xue on 1/12/17.
 */
public class FailStrategy extends Strategy {
    final static FailStrategy instance = new FailStrategy();
    private FailStrategy(){}
    static FailStrategy getInstance() {
        return instance;
    }

    @Override
    public Object handleSourceException(Exception ex, Context context) throws Exception{
        throw ex;
    }

    @Override
    public Object handleDecorException(Exception ex, Context context, Object data) throws ConfigException, SourceException, DecorException{
        if (ex instanceof ConfigException) throw (ConfigException) ex;
        if (ex instanceof SourceException) throw (SourceException) ex;
        if (ex instanceof DecorException)  throw (DecorException) ex;
        throw new DecorException("Strategy found unknown exception: " + ex.getMessage() + "; class:" + ex.getClass());
    }
}

