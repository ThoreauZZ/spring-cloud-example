package com.erdaoya.springcloud.common.actuator.maven.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author erdaoya
 * @since 1.1
 */
@Configuration
public class EndPointAutoConfig {
    @Bean
    public MavenEndpoint mavenEndpoint(){
        return new MavenEndpoint();
    }
}
