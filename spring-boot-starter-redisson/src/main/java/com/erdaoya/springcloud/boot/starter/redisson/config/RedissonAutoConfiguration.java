package com.erdaoya.springcloud.boot.starter.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhaozhou
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@ConditionalOnClass({RedissonClient.class})
@EnableConfigurationProperties({RedissonProperties.class})
public class RedissonAutoConfiguration {

    private final RedissonProperties redissionProperties;

    @Autowired
    public RedissonAutoConfiguration(RedissonProperties redissionProperties) {
        this.redissionProperties = redissionProperties;
    }


    @Bean("redissonClient")
    public RedissonClient redissonClient() {
        Config config = new Config();
        //sentinel
        if (redissionProperties.getSentinel() != null) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(redissionProperties.getSentinel().getMaster());
            sentinelServersConfig.addSentinelAddress(redissionProperties.getSentinel().getNodes().split(","));
            sentinelServersConfig.setDatabase(redissionProperties.getDatabase());
            if (redissionProperties.getPassword() != null) {
                sentinelServersConfig.setPassword(redissionProperties.getPassword());
            }
            return Redisson.create(config);
        }
        // cluster
        if (redissionProperties.getCluster() != null) {
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            List<String> nodes = redissionProperties.getCluster().getNodes();
            for (String address : nodes) {
                clusterServersConfig.addNodeAddress(address);
            }
            Redisson.create(config);
            return Redisson.create(config);
        }
        // single redis
        SingleServerConfig singleServerConfig = config.useSingleServer();
        String schema = redissionProperties.isSsl() ? "rediss://" : "redis://";
        singleServerConfig.setAddress(schema + redissionProperties.getHost() + ":" + redissionProperties.getPort());
        singleServerConfig.setDatabase(redissionProperties.getDatabase());
        if (redissionProperties.getPassword() != null) {
            singleServerConfig.setPassword(redissionProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
