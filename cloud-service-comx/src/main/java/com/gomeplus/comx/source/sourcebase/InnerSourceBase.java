package com.gomeplus.comx.source.sourcebase;

import com.alibaba.fastjson.JSONObject;
import com.gomeplus.comx.boot.ComxConfLoader;
import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.schema.Schema;
import com.gomeplus.comx.schema.SchemaLoader;
import com.gomeplus.comx.schema.datadecor.DecorException;
import com.gomeplus.comx.schema.datadecor.DecorFactory;
import com.gomeplus.comx.schema.datadecor.decors.AbstractDecor;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.rest.RequestMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xue on 1/12/17.
 */
public class InnerSourceBase extends AbstractRequestBasedSourceBase{
    public InnerSourceBase(Config config) {
        super(config);
    }

    protected Object doRequest(RequestMessage request, Context context) throws Exception {
            Context newContext = context.copy();
            newContext.setRequest(request);
            String path = request.getUrl().getRelatedPath(ComxConfLoader.getUrlPrefix());

            Object data = new HashMap<String, Object>();
            Schema schema = SchemaLoader.load(path, "get");
            newContext.setSchema(schema);
            AbstractDecor rootdecor = DecorFactory.create(schema.getConf(), AbstractDecor.TYPE_ROOT);
            rootdecor.decorate(data, newContext);

            context.getLogger().debug("" + conf.rawData());
            context.getLogger().debug("InnerSourceBase :" + new JSONObject((Map)data).toJSONString());
            return data;
    }
    public String getUrlPrefix(Context context) {
        return ComxConfLoader.getUrlPrefix();
        //return context.getUrlPrefix();
    }
}
