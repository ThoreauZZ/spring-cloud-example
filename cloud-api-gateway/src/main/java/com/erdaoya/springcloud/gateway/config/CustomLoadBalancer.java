package com.erdaoya.springcloud.gateway.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;

/**
 *  fix: ribbon:ServerListRefreshInterval ignored
 *  // TODO remove after 1.3.0.RELEASE
 *
 * @author erdaoya
 */
public class CustomLoadBalancer {
    @Bean
    public ILoadBalancer ribbonLoadBalancer(IClientConfig config,
                                            ServerList<Server> serverList, ServerListFilter<Server> serverListFilter,
                                            IRule rule, IPing ping) {
        return new ZoneAwareLoadBalancer<>(config, rule, ping, serverList,
                serverListFilter, new PollingServerListUpdater(config));
    }
}
