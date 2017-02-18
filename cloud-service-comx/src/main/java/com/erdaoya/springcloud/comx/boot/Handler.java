package com.erdaoya.springcloud.comx.boot;

import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.schema.SchemaLoader;
import com.erdaoya.springcloud.comx.schema.datadecor.DecorFactory;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.rest.ResponseMessage;
import com.erdaoya.springcloud.comx.utils.rest.Url;
import com.erdaoya.springcloud.comx.schema.Schema;
import com.erdaoya.springcloud.comx.schema.datadecor.DecorException;
import com.erdaoya.springcloud.comx.schema.datadecor.decors.AbstractDecor;
import com.erdaoya.springcloud.comx.source.SourceException;

/**
 * Created by xue on 12/19/16.
 */
// 处理流程
public class Handler {
    public void handle(Context context) {
        try {
            // 记录进入日志
            Url url         = context.getRequest().getUrl();
            // 规范约定应当为map形式， 但最终并不强制要求是
            JSONObject data = new JSONObject();
            context.getLogger().info("Handle Url:" + url.getUrl());
            String method = context.getRequest().getMethod();

            Schema schema   = SchemaLoader.load(url.get("path").toString(), method.toLowerCase());
            context.setSchema(schema);
            // TODO 处理登录验证
            AbstractDecor rootdecor = DecorFactory.create(schema.getConf(), AbstractDecor.TYPE_ROOT);
            rootdecor.decorate(data, context);
            ResponseMessage responseMessage = context.getResponse();
            responseMessage.setData(data);
        } catch (ConfigException ex) {
            ex.printStackTrace();
            context.getLogger().error(ex.getMessage());
            context.getResponse().setMessage(ex.getMessage());
        } catch (SourceException ex){
            ex.printStackTrace();
            context.getLogger().error(ex.getMessage());
            context.getResponse().setMessage(ex.getMessage());
        } catch (DecorException ex) {
            ex.printStackTrace();
            context.getLogger().error(ex.getMessage());
            context.getResponse().setMessage(ex.getMessage());
        }
    }
}
