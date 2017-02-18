package com.erdaoya.springcloud.comx.context;

import com.erdaoya.springcloud.comx.boot.ComxConfLoader;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;
import com.erdaoya.springcloud.comx.utils.log.ComxLogger;
import com.erdaoya.springcloud.comx.utils.rest.ResponseMessage;


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
