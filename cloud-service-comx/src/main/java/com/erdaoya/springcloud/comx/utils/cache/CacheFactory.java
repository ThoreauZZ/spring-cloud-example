package com.erdaoya.springcloud.comx.utils.cache;

import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.config.Config;

import java.util.HashMap;

/**
 * Created by xue on 1/3/17.
 */
public class CacheFactory {
    private static HashMap<Config, JedisClusterCache> instances = new HashMap<>();

    // Todo 加锁 不过因为幂等暂时简单实现
    public static JedisClusterCache fromConf(Config config) throws ConfigException {
        if (!instances.containsKey(config)) {
            if (config.str("engine", "").equals("redis") || config.str("type", "").equals("redis")) {
                if (config.sub("redis").sub("options").str("cluster", "").equals("redis")) {
                    instances.put(config, new JedisClusterCache(config));
                }
            }
        }
        return instances.get(config);
    }
}
