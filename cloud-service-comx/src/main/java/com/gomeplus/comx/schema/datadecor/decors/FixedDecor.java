package com.gomeplus.comx.schema.datadecor.decors;


import com.alibaba.fastjson.JSONObject;
import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;

import java.util.Map;

/**
 * Created by xue on 1/17/17.
 * TODO 还没写完。。
 * TODO refjsonpath
 */
public class FixedDecor extends AbstractDecor {
    static final String FIELD_FIXED_DATA    = "fixedData";
    static final String FIELD_FIELD         = "field";

    public FixedDecor(Config conf) {
        super(conf);
    }

    public void doDecorate(Object data, Context context) throws ConfigException{
        JSONObject loaded;
        String field        = conf.str (FIELD_FIELD, "");
        if (field.isEmpty()) {
            loaded   = conf.rsub(FIELD_FIXED_DATA).rawData();
        } else {
            loaded   = new JSONObject();
            loaded.put(field, conf.rsub(FIELD_FIXED_DATA).rawData());
        }

        if (data instanceof Map) {
            for (String key: loaded.keySet()) {
                Object value = loaded.get(key);
                context.getLogger().error("FixedDecor: merge key :" + key + " value:" + value);
                ((Map)data).put(key, loaded.get(key));
            }
        } else {
            context.getLogger().error("FixedDecor: found data not hash:" + data);
        }
    }
    public String getType() {
        return AbstractDecor.TYPE_FIXED;
    }
}
