package com.erdaoya.springcloud.boot.starter.redisson.annotation;

import com.erdaoya.springcloud.boot.starter.redisson.config.RedissonAutoConfiguration;
import com.erdaoya.springcloud.boot.starter.redisson.config.RedissonHealthIndicator;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnClass(AbstractHealthIndicator.class)
@Import({RedissonAutoConfiguration.class, RedissonHealthIndicator.class})
public @interface EnableRedissionClient {
}
