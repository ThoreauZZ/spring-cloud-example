package com.erdaoya.springcloud.config.config;

import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 17/9/30 下午8:02.
 *
 * @author thoreau
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Import(MysqlEnvironmentRepositoryConfiguration.class)
@EnableConfigServer
public @interface EnableMysqlConfigServer {
}
