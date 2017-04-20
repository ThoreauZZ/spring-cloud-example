package com.erdaoya.springcloud.comx.source.sourcebase;

import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;

/**
 * Created by xue on 12/23/16.
 */
public class HttpSourceBase extends AbstractRequestBasedSourceBase{
    static final String FIELD_URL_PREFIX = "urlPrefix";

    public HttpSourceBase(Config conf){super(conf);}

    protected Object doRequest(RequestMessage request, Context context) throws SourceException{
        // TODO 处理请求前数据
        // 处理 traceId
        // 处理 X-Forwarded-For

        return request.execute(context).getData();
    }

    public String getUrlPrefix(Context context) throws ConfigException {
        return conf.rstr(FIELD_URL_PREFIX);
    }
}
