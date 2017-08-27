package com.erdaoya.springcloud.boot.starter.redisson.config;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * 17/8/27 上午1:08.
 *
 * @author zhaozhou
 */
public class RedissonCreateFailureAnalyzer extends AbstractFailureAnalyzer<BeanCreationException> {

    @Override
    protected FailureAnalysis analyze(Throwable cause, BeanCreationException e) {
        String message = cause.getMessage();
        String description = message.trim();
        String action = "1. add config in application.properties/yml\n" +
                "2. add @EnableRedissionClient on main  start class \n";
        return new FailureAnalysis(description, action, cause);
    }
}
