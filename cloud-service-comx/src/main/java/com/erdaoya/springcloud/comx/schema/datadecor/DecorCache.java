package com.erdaoya.springcloud.comx.schema.datadecor;

import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.schema.TinyTemplate;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;

import java.util.HashMap;

/**
 * Created by xue on 12/20/16.
 */
public class DecorCache {
    public static final String FIELD_KEY = "key";
    public static final String FIELD_TTL = "ttl";
    public static final String FIELD_WITH_CHILDREN  = "withChildren";
    public static final String FIELD_IS_GLOBAL      = "isGlobal";
    public static final String KEY_PREFIX           = "Decor:";

    protected String    key;
    protected Integer   ttlMs;
    protected boolean   withChildren;
    protected boolean   isGlobal =false;

    protected DecorCache (String key, Integer ttlMs, boolean withChildren, boolean isGlobal) {
        this.isGlobal = isGlobal;
        this.key = key;
        this.ttlMs = ttlMs;
        this.withChildren = withChildren;
    }
    protected DecorCache (String key, Integer ttlMs, boolean withChildren) {
        this(key, ttlMs, withChildren, false);
    }

    /**
     * decorCache 并非主流程 实现稍后
     * TODO  key 细节需要确认
     * @param config
     * @param context
     * @param data
     * @return
     * @throws ConfigException
     */
    public static DecorCache fromConf(Config config, Context context, Object data) throws ConfigException{
        if(config.rawData().isEmpty()) {
            return new NullDecorCache();
        }
        TinyTemplate keyTpl = new TinyTemplate(config.rstr(DecorCache.FIELD_KEY));
        // TODO 添加 global 分支
        String prefix = context.getRequest().getUrl().get("path") + ":";
        HashMap vars = new HashMap();
        vars.put("data", data);
        vars.put("request", context.getRequest());
        String key =  prefix + keyTpl.render(vars, context, true);

        return new DecorCache(key, config.rintvalue(DecorCache.FIELD_TTL), config.bool(DecorCache.FIELD_WITH_CHILDREN, false));
    }
}
