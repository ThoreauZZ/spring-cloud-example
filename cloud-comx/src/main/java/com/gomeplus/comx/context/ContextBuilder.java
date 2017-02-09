package com.gomeplus.comx.context;

import com.gomeplus.comx.boot.ComxConfLoader;
import com.gomeplus.comx.utils.config.ConfigException;
import com.gomeplus.comx.utils.rest.RequestMessage;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.log.ComxLogger;
import com.gomeplus.comx.utils.rest.ResponseMessage;


/**
 * Created by xue on 12/15/16.
 */
public class ContextBuilder {
    /**
     * @return Context context
     */
    public static Context build(RequestMessage request) throws ConfigException{
        ComxConfLoader.load();
        String traceId      = request.initTraceId();
        Context context     = new Context();
        User user           = new User(request);
        ContextCache cache  = new ContextCache(ComxConfLoader.getCache(), "1".equals(request.getUrl().getQuery().get("__refresh")));
        ComxLogger logger   = new ComxLogger();
        ResponseMessage responseMessage = new ResponseMessage();


        context.setLogger(logger);
        context.setRequest(request);
        context.setUser(user);
        context.setCache(cache);
        context.setResponse(responseMessage);

        logger.setTraceId(traceId);
        context.setTraceId(traceId);
        //TODO 重构 resttemplate 不应当放在context 当中
        context.setRestTemplate(request.getRestTemplate());
        // TODO jsonp
        // 在外部
        return context;
    }


}
