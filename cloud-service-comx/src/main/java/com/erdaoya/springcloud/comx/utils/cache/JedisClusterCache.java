package com.erdaoya.springcloud.comx.utils.cache;

import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.config.Config;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xue on 12/29/16.
 *
 * Pass config and return Single JedisCluster
 * since Jedis pool is multithread-safe, it's not neccessary to get a JedisCluster pool;
 * 并封装 不同版本jedis 不一样 函数
 */
public class JedisClusterCache extends AbstractCache{


    private JedisCluster jc;


    JedisClusterCache(Config config) throws ConfigException {
        super(config);
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();

        Config servers = config.rsub("redis").rsub("servers");
        for (String key:servers.keys()) {
            Config conf = servers.sub(key);
            Integer port = conf.rintvalue("port");
            String host  = conf.rstr("host");
            jedisClusterNodes.add(new HostAndPort(host, port));
        }
        jc = new JedisCluster(jedisClusterNodes);
    }

    public String get(String key) {
        return jc.get(key);
    }
    public String set(String key, String value) {
        return jc.set(key, value);
    }
    public String set(String key, String value, Integer time) {
        return jc.setex(key, time, value);
    }
}
