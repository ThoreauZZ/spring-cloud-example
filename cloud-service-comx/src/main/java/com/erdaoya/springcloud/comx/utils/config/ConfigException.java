package com.erdaoya.springcloud.comx.utils.config;

/**
 * Created by xue on 12/7/16.
 */
public class ConfigException  extends Exception{
    public ConfigException(String message) {
        super(message);
    }
    public ConfigException(Throwable throwable) {
        super(throwable);
    }
}
