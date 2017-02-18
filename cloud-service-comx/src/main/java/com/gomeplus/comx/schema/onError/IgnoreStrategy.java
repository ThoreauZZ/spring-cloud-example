package com.gomeplus.comx.schema.onError;

import com.gomeplus.comx.context.Context;

/**
 * Created by xue on 1/12/17.
 */
public class IgnoreStrategy extends Strategy {
    final static IgnoreStrategy instance = new IgnoreStrategy();
    private IgnoreStrategy(){}
    static IgnoreStrategy getInstance() {
        return instance;
    }

    @Override
    public Object handleSourceException(Exception ex, Context context) {
        context.getLogger().error("OnError IgnoreStrategy,source exception:" + ex.getMessage() + "; class:" + ex.getClass());
        return null;
    }

    @Override
    public Object handleDecorException(Exception ex, Context context, Object data) {
        context.getLogger().error("OnError IgnoreStrategy,decor exception:" + ex.getMessage() + "; class:" + ex.getClass());
        return null;
    }
}
