package com.erdaoya.springcloud.comx.schema.datadecor.decors;


import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;

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

    // TODO 确认 loaded 是否 一定是 json, 是否可以是 literal value
    public void doDecorate(Object data, Context context) throws ConfigException {
        String field        = conf.str (FIELD_FIELD, "");

        JSONObject loaded = field.isEmpty()? conf.rsub(FIELD_FIXED_DATA).rawData(): new JSONObject() {{ put(field, conf.rsub(FIELD_FIXED_DATA).rawData()); }};

        if (data instanceof Map) {
            for (String key: loaded.keySet()) {
                Object value = loaded.get(key);
                context.getLogger().info("FixedDecor: merge key :" + key + " value:" + value);
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
