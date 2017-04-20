package com.erdaoya.springcloud.comx.source.sourcebase;

import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.config.Config;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by xue on 12/23/16.
 *
 */
abstract public class AbstractSourceBase {
    static final String FIELD_ID = "id";
    protected Config conf;
    public AbstractSourceBase(Config conf) {this.conf = conf;}

    public String getId() throws ConfigException { return conf.rstr(AbstractSourceBase.FIELD_ID);}
    abstract public Object executeLoading(Context context, Config sourceOptions, HashMap<String, Object> reservedVariables) throws ConfigException, SourceException, IOException;
}
