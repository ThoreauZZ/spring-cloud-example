package com.gomeplus.comx.source.sourcebase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.source.Source;
import com.gomeplus.comx.source.SourceException;
import com.gomeplus.comx.utils.cache.CacheFactory;
import com.gomeplus.comx.utils.cache.JedisClusterCache;
import com.gomeplus.comx.utils.config.Config;
import com.gomeplus.comx.utils.config.ConfigException;

import java.util.HashMap;

/**
 * Created by xue on 12/23/16.
 * 仅仅是资源
 * 只按最新写法
 */
// TODO
public class RedisSourceBase extends AbstractSourceBase{
    public RedisSourceBase(Config conf) {
        super(conf);
    }
    public JedisClusterCache getRedisClient() throws ConfigException{
        return CacheFactory.fromConf(conf);
    }
    public Object executeLoading(Context context, Config sourceOptions, HashMap<String, Object> reservedVariables) throws ConfigException, SourceException{
        String uriTpl = (String)reservedVariables.get(Source.RESERVED_RENDERED_URI);
        String result = getRedisClient().get(uriTpl);
        if (result == null) {
            throw new SourceException("Source RedisSourcebase: null, key:" + uriTpl);
        }
        try {
            return JSON.parse(result);
        } catch (Exception ex) {
            throw new SourceException("Source RedisSourceBase: failed to undecode str:" + result);
        }
    }

}




