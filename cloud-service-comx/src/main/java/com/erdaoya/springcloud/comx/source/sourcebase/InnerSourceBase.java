package com.erdaoya.springcloud.comx.source.sourcebase;

import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.schema.Schema;
import com.erdaoya.springcloud.comx.boot.ComxConfLoader;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.schema.SchemaLoader;
import com.erdaoya.springcloud.comx.schema.datadecor.DecorFactory;
import com.erdaoya.springcloud.comx.schema.datadecor.decors.AbstractDecor;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xue on 1/12/17.
 */
public class InnerSourceBase extends AbstractRequestBasedSourceBase{
    public InnerSourceBase(Config config) {
        super(config);
    }

    // TODO 这里只能进行包装
    protected Object doRequest(RequestMessage request, Context context) throws SourceException {
        try {
            Context newContext = context.copy();
            newContext.setRequest(request);
            String path = request.getUrl().getRelatedPath(ComxConfLoader.getUrlPrefix());

            Object data = new HashMap<String, Object>();
            Schema schema = SchemaLoader.load(path, "get");
            newContext.setSchema(schema);
            AbstractDecor rootdecor = DecorFactory.create(schema.getConf(), AbstractDecor.TYPE_ROOT);
            rootdecor.decorate(data, newContext);

            context.getLogger().debug("" + conf.rawData());
            context.getLogger().debug("InnerSourceBase :" + new JSONObject((Map) data).toJSONString());
            // TODO 验证逻辑
            if (((Map) data).isEmpty()) return null;
            return data;
        } catch (Exception ex) {
           throw new SourceException(ex);
        }
    }
    public String getUrlPrefix(Context context) {
        return ComxConfLoader.getUrlPrefix();
        //return context.getUrlPrefix();
    }
}
