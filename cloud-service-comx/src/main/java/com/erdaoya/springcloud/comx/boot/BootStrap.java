package com.erdaoya.springcloud.comx.boot;

/**
 * Created by xue on 12/16/16.
 */

import com.alibaba.fastjson.JSONArray;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.context.ContextBuilder;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;
import com.erdaoya.springcloud.comx.utils.rest.ResponseMessage;

import java.util.List;

/**
 * 业务无关日志
 * sandbox and meta
 * read comx.conf.json
 * request;
 */
public class BootStrap {
    public static ResponseMessage start(RequestMessage requestMessage) {
        Context context;
        try {
            context = ContextBuilder.build(requestMessage);
            //TODO debug mode for meta and sandbox

            Handler handler = new Handler();
            handler.handle(context);
        } catch(Exception ex){
            //TODO 处理各种exception
            // 只处理初始化错误，业务错误在handler 处理
            // 构建初始环境异常 打印日志，返回服务不可用
            ex.printStackTrace();
            // 是否针对 jsonp 做特殊处理
            return new ResponseMessage(null, ex.getMessage(),500);
        }

        ResponseMessage response = context.getResponse();
        response.setDebug(new JSONArray((List)context.getLogger().getDebugInfo()));
        // TODO 处理 jsonp


        return context.getResponse();
    }
}
