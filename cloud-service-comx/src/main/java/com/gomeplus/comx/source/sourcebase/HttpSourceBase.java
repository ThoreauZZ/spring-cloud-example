package com.gomeplus.comx.source.sourcebase;

import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;
import com.gomeplus.comx.utils.rest.RequestMessage;

import java.io.IOException;

/**
 * Created by xue on 12/23/16.
 */
public class HttpSourceBase extends AbstractRequestBasedSourceBase{
    static final String FIELD_URL_PREFIX = "urlPrefix";

    public HttpSourceBase(Config conf){super(conf);}

    protected Object doRequest(RequestMessage request, Context context) throws IOException{
        // TODO 处理请求前数据
        // 处理 traceId
        // 处理 X-Forwarded-For

        return request.execute(context).getData();
    }

    public String getUrlPrefix(Context context) throws ConfigException {
        return conf.rstr(FIELD_URL_PREFIX);
    }
}
