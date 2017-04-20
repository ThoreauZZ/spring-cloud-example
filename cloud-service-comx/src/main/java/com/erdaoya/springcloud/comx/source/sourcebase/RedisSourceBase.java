package com.erdaoya.springcloud.comx.source.sourcebase;

import com.alibaba.fastjson.JSON;
import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.source.Source;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.cache.CacheFactory;
import com.erdaoya.springcloud.comx.utils.cache.JedisClusterCache;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;

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
    public JedisClusterCache getRedisClient() throws ConfigException {
        return CacheFactory.fromConf(conf);
    }
    public Object executeLoading(Context context, Config sourceOptions, HashMap<String, Object> reservedVariables) throws ConfigException, SourceException {
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




