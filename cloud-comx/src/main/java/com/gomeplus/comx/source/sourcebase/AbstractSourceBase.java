package com.gomeplus.comx.source.sourcebase;

import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;
import com.gomeplus.comx.utils.rest.UrlException;

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

    public String getId() throws ConfigException{ return conf.rstr(AbstractSourceBase.FIELD_ID);}
    abstract public Object executeLoading(Context context, Config sourceOptions, HashMap<String, Object> reservedVariables) throws ConfigException, SourceException, IOException;
}
