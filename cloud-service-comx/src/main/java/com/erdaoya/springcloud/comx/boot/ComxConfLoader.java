package com.erdaoya.springcloud.comx.boot;

import com.erdaoya.springcloud.comx.source.sourcebase.SourceBaseFactory;
import com.erdaoya.springcloud.comx.utils.cache.AbstractCache;
import com.erdaoya.springcloud.comx.utils.cache.CacheFactory;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.config.Loader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xue on 12/16/16.
 * 在服务启动时加载配置
 */
@Slf4j
public class ComxConfLoader {
    private static boolean              initialized = false;
    private static String               COMX_HOME;
    private static Config comxConf;
    private static SourceBaseFactory sourceBaseFactory;
    private static AbstractCache cache;
    private static String               urlPrefix;



    private static final String FIELD_URL_PREFIX    = "urlPrefix";
    private static final String FIELD_CACHE         = "cache";

    static {
        Properties prop = new Properties();
        InputStream in = ComxConfLoader.class.getClassLoader().getResourceAsStream("comx.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            log.error("fail to load properties!", e);
            throw new RuntimeException(e);
        }
        COMX_HOME = prop.getProperty("comx_home");
    }


    // 提供更新方法 TODO
    public static Config load()  throws ConfigException {
        if (!initialized) {
            initialize();
        }
        return comxConf;
    }

    public static void initialize()  throws ConfigException{
        comxConf            = Loader.fromJsonFile(COMX_HOME + "/comx.conf.json");
        sourceBaseFactory   = SourceBaseFactory.fromConf(comxConf);
        urlPrefix           = comxConf.rstr(FIELD_URL_PREFIX);
        Config cacheConf    = comxConf.sub(FIELD_CACHE);
        cache               = CacheFactory.fromConf(cacheConf);
        initialized         = true;
    }






    public static String getUrlPrefix() {
        return urlPrefix;
    }

    public static AbstractCache getCache() {return cache;}

    public static String getComxHome() {
        return COMX_HOME;
    }

    public static Config getComxConf() {
        return comxConf;
    }

    public static SourceBaseFactory getSourceBaseFactory() {
        return sourceBaseFactory;
    }
}
