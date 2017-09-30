package com.erdaoya.springcloud.config.config;

import com.erdaoya.springcloud.config.environment.MysqlEnvironmentRepository;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 17/9/30 下午8:04.
 *
 * @author thoreau
 */

@Configuration
public class MysqlEnvironmentRepositoryConfiguration {
    @Bean
    public EnvironmentRepository environmentRepository() {
        return new MysqlEnvironmentRepository();
    }
}
