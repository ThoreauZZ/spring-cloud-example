package com.erdaoya.springcloud.comx.context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erdaoya.springcloud.comx.utils.cache.AbstractCache;

import java.util.Map;

/**
 * Created by xue on 1/3/17.
 * context cache 业务相关及日志等 封装在这里，业务直接调用，不直接使用utils/cache
 * 每个请求将有一个contextcache 实例 , utils/cache 同一个
 *
 */
public class ContextCache {
    private AbstractCache cache;
    private Boolean refreshingEnabled;
    public ContextCache(AbstractCache cache, Boolean refreshingEnabled) {
        this.cache = cache;
        this.refreshingEnabled = refreshingEnabled;
    }

    public String setMapObject(String key, Map value, Integer time) {
        //System.out.println("set "+ key);
        JSONObject jsonObject = new JSONObject(value);
        return cache.set(key, jsonObject.toJSONString(), time);
    }
    public Object getMapObject(String key) {
        //System.out.println("get "+ key);
        if (refreshingEnabled) return null;
        String value = cache.get(key);
        try {
            return JSON.parseObject(value);
        } catch (Exception ex) {
            // TODO 解析失败 记录日志
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return value;
        }
    }



    public String set(String key, String value, Integer time) {
        return cache.set(key, value, time);
    }
    public String set(String key, String value) {
        return cache.set(key, value);
    }
    public String get(String key) {
        if (refreshingEnabled) return null;
        return cache.get(key);
    }
}
