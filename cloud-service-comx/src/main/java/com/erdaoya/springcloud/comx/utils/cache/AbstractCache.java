package com.erdaoya.springcloud.comx.utils.cache;

import com.erdaoya.springcloud.comx.utils.config.Config;

/**
 * Created by xue on 1/3/17.
 */
public abstract class AbstractCache {
    public static final Integer MAX_KEY_LENGTH = 255;
    private Config config;

    public AbstractCache(Config config) {
        this.config = config;
    }

    public abstract String set(String key, String value);
    public abstract String set(String key, String value, Integer time);
    public abstract String get(String key);

    public Boolean validateKey(String key){
        //TODO validates
        return true;
    }
}
