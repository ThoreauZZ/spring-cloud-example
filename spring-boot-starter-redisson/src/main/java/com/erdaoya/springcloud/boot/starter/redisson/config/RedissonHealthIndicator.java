package com.erdaoya.springcloud.boot.starter.redisson.config;

import org.redisson.api.Node;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 17/8/27 下午1:44.
 *
 * @author zhaozhou
 */
@Component("redisson")
public class RedissonHealthIndicator extends AbstractHealthIndicator {
    @Autowired
    private RedissonClient redissonClient;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            Collection<Node> nodes = redissonClient.getNodesGroup().getNodes();
            String version = "Unknown";
            int nodeSize = 0;
            for (Node node : nodes) {
                nodeSize++;
                version = node.info(Node.InfoSection.SERVER).get("redis_version");
            }
            builder.up().withDetail("version", version).withDetail("nodeSize", nodeSize).build();
        } catch (Exception cause) {
            builder.down(cause).build();
        }
    }
}
